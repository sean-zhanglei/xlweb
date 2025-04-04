package com.nbug.module.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.system.SystemCity;
import com.nbug.mico.common.model.user.UserAddress;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserAddressRequest;
import com.nbug.module.system.api.city.CityApi;
import com.nbug.module.user.dal.UserAddressDao;
import com.nbug.module.user.service.UserAddressService;
import com.nbug.module.user.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * UserAddressServiceImpl 接口实现

 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressDao, UserAddress> implements UserAddressService {

    @Resource
    private UserAddressDao dao;

    @Autowired
    private CityApi cityApi;

    @Autowired
    private UserService userService;

    /**
    * 列表
    * @return List<UserAddress>
    */
    @Override
    public List<UserAddress> getList(PageParamRequest pageParamRequest) {
        Integer UserId = userService.getUserIdException();
        PageHelper.startPage(pageParamRequest.getPage(), pageParamRequest.getLimit());
        LambdaQueryWrapper<UserAddress> lqw = Wrappers.lambdaQuery();
        lqw.select(UserAddress::getId, UserAddress::getRealName, UserAddress::getPhone, UserAddress::getProvince,
                UserAddress::getCity, UserAddress::getDistrict, UserAddress::getDetail, UserAddress::getIsDefault);
        lqw.eq(UserAddress::getUid, UserId);
        lqw.eq(UserAddress::getIsDel, false);
        lqw.orderByDesc(UserAddress::getIsDefault);
        lqw.orderByDesc(UserAddress::getId);
        return dao.selectList(lqw);
    }

    /**
     * 创建地址
     * @param request UserAddressRequest 参数
     * @return List<UserAddress>
     */
    @Override
    public UserAddress create(UserAddressRequest request) {
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(request, userAddress);
        userAddress.setCity(request.getAddress().getCity());
        userAddress.setCityId(request.getAddress().getCityId());
        userAddress.setDistrict(request.getAddress().getDistrict());
        userAddress.setProvince(request.getAddress().getProvince());

        // 添加地址时cityId和城市名称不能同时为空，如果id为空，必须用城市名称自查后set CityId
        if (request.getAddress().getCityId() == 0 && StringUtils.isBlank(request.getAddress().getCity())) {
            throw new XlwebException("请选择正确城市数据");
        }
        if (StringUtils.isNotBlank(request.getAddress().getCity()) && request.getAddress().getCityId() == 0) {
            SystemCity currentCity = cityApi.getCityByCityName(request.getAddress().getCity()).getCheckedData();
            if (ObjectUtil.isNull(currentCity)) throw new XlwebException("当前城市未找到！");

            userAddress.setCityId(currentCity.getCityId());
        }

        if (request.getAddress().getCityId() > 0 && StringUtils.isNotBlank(request.getAddress().getCity())) {
            checkCity(userAddress.getCityId());
        }
        userAddress.setUid(userService.getUserIdException());
        if (userAddress.getIsDefault()) {
            //把当前用户其他默认地址取消
            cancelDefault(userAddress.getUid());
        }
        saveOrUpdate(userAddress);
        return userAddress;
    }

    /**
     * 设置默认
     * @param id Integer id
     * @return UserAddress
     */
    @Override
    public Boolean def(Integer id) {
        //把当前用户其他默认地址取消
        cancelDefault(userService.getUserIdException());
        UserAddress userAddress = new UserAddress();
        userAddress.setId(id);
        userAddress.setUid(userService.getUserIdException());
        userAddress.setIsDefault(true);
        return updateById(userAddress);
    }

    /**
     * 删除
     * @param id Integer id
     * @return UserAddress
     */
    @Override
    public Boolean delete(Integer id) {
        //把当前用户其他默认地址取消
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getId, id);
        lambdaQueryWrapper.eq(UserAddress::getUid, userService.getUserIdException());
        dao.delete(lambdaQueryWrapper);
        return true;
    }

    /**
     * 获取默认地址
     * @return UserAddress
     */
    @Override
    public UserAddress getDefault() {
        //把当前用户其他默认地址取消
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getIsDefault, true);
        lambdaQueryWrapper.eq(UserAddress::getUid, userService.getUserId());
        return dao.selectOne(lambdaQueryWrapper);
    }

    @Override
    public UserAddress getById(Integer addressId) {
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getId, addressId);
        lambdaQueryWrapper.eq(UserAddress::getIsDel, false);
        return dao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 获取地址详情
     * @param id 地址id
     * @return UserAddress
     */
    @Override
    public UserAddress getDetail(Integer id) {
        Integer UserId = userService.getUserIdException();
        LambdaQueryWrapper<UserAddress> lqw = Wrappers.lambdaQuery();
        lqw.select(UserAddress::getId, UserAddress::getRealName, UserAddress::getPhone, UserAddress::getProvince,
                UserAddress::getCity, UserAddress::getDistrict, UserAddress::getDetail, UserAddress::getIsDefault);
        lqw.eq(UserAddress::getId, id);
        lqw.eq(UserAddress::getUid, UserId);
        lqw.eq(UserAddress::getIsDel, false);
        return dao.selectOne(lqw);
    }

    /**
     * 获取默认地址
     * @return UserAddress
     */
    @Override
    public UserAddress getDefaultByUid(Integer uid) {
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(UserAddress::getIsDefault, true);
        lambdaQueryWrapper.eq(UserAddress::getUid, uid);
        return dao.selectOne(lambdaQueryWrapper);
    }

    /**
     * 检测城市id是否合法
     * @param cityId Integer 城市id
     */
    private void checkCity(Integer cityId) {
        //检测城市Id是否存在
        SystemCity systemCity = cityApi.getCityByCityId(cityId).getCheckedData();
        if (ObjectUtil.isNull(systemCity)) {
            throw new XlwebException("请选择正确的城市");
        }
    }

    /**
     * 取消默认地址
     * @param userId Integer 城市id
     */
    private void cancelDefault(Integer userId) {
        //检测城市Id是否存在
        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(false);
        LambdaQueryWrapper<UserAddress> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserAddress::getUid, userId);
        update(userAddress, lambdaQueryWrapper);
    }

}

