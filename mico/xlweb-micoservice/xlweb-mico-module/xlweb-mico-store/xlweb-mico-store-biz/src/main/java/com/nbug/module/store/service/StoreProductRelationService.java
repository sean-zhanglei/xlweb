package com.nbug.module.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.product.StoreProductRelation;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserCollectAllRequest;
import com.nbug.mico.common.request.UserCollectRequest;
import com.nbug.mico.common.response.UserRelationResponse;

import java.util.List;

/**
 * StoreProductRelationService 接口

 */
public interface StoreProductRelationService extends IService<StoreProductRelation> {

    List<StoreProductRelation> getList(Integer productId, String type);

    /**
     * 取消收藏
     * @param requestJson 收藏idsJson
     * @return Boolean
     */
    Boolean delete(String requestJson);

    /**
     * 批量收藏
     * @param request 收藏参数
     * @return Boolean
     */
    Boolean all(UserCollectAllRequest request);

    List<StoreProductRelation> getLikeOrCollectByUser(Integer userId, Integer productId,boolean isLike);

    /**
     * 获取用户收藏列表
     * @param pageParamRequest 分页参数
     * @return List<UserRelationResponse>
     */
    List<UserRelationResponse> getUserList(PageParamRequest pageParamRequest);

    /**
     * 获取用户的收藏数量
     * @param uid 用户uid
     * @return 收藏数量
     */
    Integer getCollectCountByUid(Integer uid);

    /**
     * 根据商品Id取消收藏
     * @param proId 商品Id
     * @return Boolean
     */
    Boolean deleteByProId(Integer proId);

    /**
     * 根据商品Id取消收藏
     * @param proId 商品Id
     * @return Boolean
     */
    Boolean deleteByProIdAndUid(Integer proId);

    /**
     * 根据日期获取收藏量
     * @param date 日期，yyyy-MM-dd格式
     * @return Integer
     */
    Integer getCountByDate(String date);

    /**
     * 根据日期获取收藏量
     * @param date 日期，yyyy-MM-dd格式
     * @param proId 商品id
     * @return Integer
     */
    Integer getCountByDateAndProId(String date, Integer proId);

    /**
     * 添加收藏
     * @param request 收藏参数
     */
    Boolean add(UserCollectRequest request);
}
