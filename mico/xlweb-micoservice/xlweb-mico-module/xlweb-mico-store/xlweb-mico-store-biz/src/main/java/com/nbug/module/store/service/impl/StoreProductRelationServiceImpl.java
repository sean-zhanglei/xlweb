package com.nbug.module.store.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.product.StoreProductRelation;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCollectAllRequest;
import com.nbug.mico.common.request.UserCollectRequest;
import com.nbug.mico.common.response.UserRelationResponse;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.store.dal.StoreProductRelationDao;
import com.nbug.module.store.service.StoreProductRelationService;
import com.nbug.module.user.api.user.UserApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StoreProductRelationServiceImpl 接口实现

 */
@Service
public class StoreProductRelationServiceImpl extends ServiceImpl<StoreProductRelationDao, StoreProductRelation>
        implements StoreProductRelationService {

    @Resource
    private StoreProductRelationDao dao;

    @Autowired
    private UserApi userApi;

    /**
     * 添加收藏产品
     * @param request UserCollectAllRequest 新增参数
     * @return boolean
     */
    @Override
    public Boolean all(UserCollectAllRequest request) {
        Integer[] arr = request.getProductId();
        if(arr.length < 1){
            throw new XlwebException("请选择产品");
        }

        List<Integer> list = XlwebUtil.arrayUnique(arr);

        Integer uid = userApi.getUserIdException().getCheckedData();
        deleteAll(request, uid, "collect");  //先删除所有已存在的

        ArrayList<StoreProductRelation> storeProductRelationList = new ArrayList<>();
        for (Integer productId: list) {
            StoreProductRelation storeProductRelation = new StoreProductRelation();
            storeProductRelation.setUid(uid);
            storeProductRelation.setType("collect");
            storeProductRelation.setProductId(productId);
            storeProductRelation.setCategory(request.getCategory());
            storeProductRelationList.add(storeProductRelation);
        }
        return saveBatch(storeProductRelationList);
    }


    /**
     * 取消收藏产品
     */
    @Override
    public Boolean delete(String requestJson) {
        JSONObject jsonObject = JSONObject.parseObject(requestJson);
        if (StrUtil.isBlank(jsonObject.getString("ids"))) {
            throw new XlwebException("收藏id不能为空");
        }
        List<Integer> idList = XlwebUtil.stringToArray(jsonObject.getString("ids"));
        if (CollUtil.isEmpty(idList)) {
            throw new XlwebException("收藏id不能为空");
        }
        Integer userId = userApi.getUserIdException().getCheckedData();
        LambdaQueryWrapper<StoreProductRelation> lqw = Wrappers.lambdaQuery();
        lqw.in(StoreProductRelation::getId, idList);
        lqw.eq(StoreProductRelation::getUid, userId);
        int delete = dao.delete(lqw);
        return delete > 0;
    }

    /**
     * 取消收藏产品
     * @param request UserCollectAllRequest 参数
     * @param type 类型
     */
    private void deleteAll(UserCollectAllRequest request, Integer uid, String type) {
        LambdaQueryWrapper<StoreProductRelation> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(StoreProductRelation::getProductId, Arrays.asList(request.getProductId()))
                .eq(StoreProductRelation::getCategory, request.getCategory())
                .eq(StoreProductRelation::getUid, uid)
                .eq(StoreProductRelation::getType, type);
        dao.delete(lambdaQueryWrapper);
    }

    /**
     * 根据产品id和类型获取对应列表
     * @param productId 产品id
     * @param type 类型
     * @return 对应结果
     */
    @Override
    public List<StoreProductRelation> getList(Integer productId, String type) {
        LambdaQueryWrapper<StoreProductRelation> lqr = new LambdaQueryWrapper<>();
        lqr.eq(StoreProductRelation::getProductId, productId);
        lqr.eq(StoreProductRelation::getType, type);
        return dao.selectList(lqr);
    }

    /**
     * 获取用户当前是否喜欢该商品
     * @param userId 用户id
     * @param productId 商品id
     * @return 是否喜欢标识
     */
    @Override
    public List<StoreProductRelation> getLikeOrCollectByUser(Integer userId, Integer productId,boolean isLike) {
        String typeValue = isLike?"like":"collect";
        LambdaQueryWrapper<StoreProductRelation> lqr = new LambdaQueryWrapper<>();
        lqr.eq(StoreProductRelation::getProductId, productId);
        lqr.eq(StoreProductRelation::getUid, userId);
        lqr.eq(StoreProductRelation::getType,typeValue);
        return dao.selectList(lqr);
    }

    /**
     * 获取用户收藏列表
     * @param pageParamRequest 分页参数
     * @return List<UserRelationResponse>
     */
    @Override
    public List<UserRelationResponse> getUserList(PageParamRequest pageParamRequest) {
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        Integer userId = userApi.getUserIdException().getCheckedData();
        return dao.getUserList(userId);
    }

    /**
     * 获取用户的收藏数量
     * @param uid 用户uid
     * @return 收藏数量
     */
    @Override
    public Integer getCollectCountByUid(Integer uid) {
        LambdaQueryWrapper<StoreProductRelation> lqr = Wrappers.lambdaQuery();
        lqr.eq(StoreProductRelation::getUid, uid);
        lqr.eq(StoreProductRelation::getType,"collect");
        return Math.toIntExact(dao.selectCount(lqr));
    }

    /**
     * 根据商品Id取消收藏
     * @param proId 商品Id
     * @return Boolean
     */
    @Override
    public Boolean deleteByProId(Integer proId) {
        LambdaQueryWrapper<StoreProductRelation> lqw = Wrappers.lambdaQuery();
        lqw.in(StoreProductRelation::getProductId, proId);
        int delete = dao.delete(lqw);
        return delete > 0;
    }

    /**
     * 根据商品Id取消收藏
     * @param proId 商品Id
     * @return Boolean
     */
    @Override
    public Boolean deleteByProIdAndUid(Integer proId) {
        Integer userId = userApi.getUserIdException().getCheckedData();
        LambdaQueryWrapper<StoreProductRelation> lqw = Wrappers.lambdaQuery();
        lqw.in(StoreProductRelation::getProductId, proId);
        lqw.eq(StoreProductRelation::getUid, userId);
        int delete = dao.delete(lqw);
        return delete > 0;
    }

    /**
     * 根据日期获取收藏量
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    @Override
    public Integer getCountByDate(String date) {
        LambdaQueryWrapper<StoreProductRelation> lqw = Wrappers.lambdaQuery();
        lqw.select(StoreProductRelation::getId);
        lqw.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        return Math.toIntExact(dao.selectCount(lqw));
    }

    /**
     * 根据日期获取收藏量
     * @param date 日期，yyyy-MM-dd格式
     * @param proId 商品id
     * @return Integer
     */
    @Override
    public Integer getCountByDateAndProId(String date, Integer proId) {
        LambdaQueryWrapper<StoreProductRelation> lqw = Wrappers.lambdaQuery();
        lqw.select(StoreProductRelation::getId);
        lqw.eq(StoreProductRelation::getProductId, proId);
        lqw.apply("date_format(create_time, '%Y-%m-%d') = {0}", date);
        return Math.toIntExact(dao.selectCount(lqw));
    }

    /**
     * 添加收藏
     * @param request 收藏参数
     */
    @Override
    public Boolean add(UserCollectRequest request) {
        StoreProductRelation storeProductRelation = new StoreProductRelation();
        BeanUtils.copyProperties(request, storeProductRelation);
        storeProductRelation.setUid(userApi.getUserIdException().getCheckedData());
        return save(storeProductRelation);
    }
}

