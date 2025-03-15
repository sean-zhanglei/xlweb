package com.nbug.module.system.api.systemGroupData;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.response.SystemGroupDataOrderStatusPicResponse;
import com.nbug.mico.common.response.UserRechargeItemResponse;
import com.nbug.mico.common.response.UserSpreadBannerResponse;
import com.nbug.mico.common.vo.SystemGroupDataRechargeConfigVo;
import com.nbug.mico.common.vo.SystemGroupDataSignConfigVo;
import com.nbug.module.system.service.SystemGroupDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SystemGroupDataApiImpl implements SystemGroupDataApi {

    @Resource
    private SystemGroupDataService systemGroupDataService;

    @Override
    public CommonResult<List<HashMap<String, Object>>> getListMapByGid(Integer gid) {
        return CommonResult.success(systemGroupDataService.getListMapByGid(gid));
    }

    /**
     * 通过gid获取订单状态图片
     * @param gid Integer group id
     * @return List<T>
     */
    @Override
    public CommonResult<List<SystemGroupDataOrderStatusPicResponse>> getGroupDataOrderStatusPicListByGid(Integer gid) {
        return CommonResult.success(systemGroupDataService.getListByGid(gid, SystemGroupDataOrderStatusPicResponse.class));
    }

    /**
     * 通过gid获取订单状态图片
     * @param gid Integer group id
     * @return List<T>
     */
    @Override
    public CommonResult<List<SystemGroupDataSignConfigVo>> getGroupDataSignConfigListByGid(Integer gid) {
        return CommonResult.success(systemGroupDataService.getListByGid(gid, SystemGroupDataSignConfigVo.class));
    }

    /**
     * 通过gid获取用户地址
     * @param gid Integer group id
     * @return List<T>
     */
    @Override
    public CommonResult<List<UserSpreadBannerResponse>> getGroupDataSpreadBannerListByGid(Integer gid) {
        return CommonResult.success(systemGroupDataService.getListByGid(gid, UserSpreadBannerResponse.class));
    }

    /**
     * 通过gid获取充值套餐
     * @param gid Integer group id
     * @return List<T>
     */
    @Override
    public CommonResult<List<UserRechargeItemResponse>> getGroupDataRechargeFrontListByGid(Integer gid) {
        return CommonResult.success(systemGroupDataService.getListByGid(gid, UserRechargeItemResponse.class));
    }

    @Override
    public CommonResult<SystemGroupDataRechargeConfigVo> getGroupDataRechargeConfig(Integer groupDataId) {
        return CommonResult.success(systemGroupDataService.getNormalInfo(groupDataId, SystemGroupDataRechargeConfigVo.class));
    }

    /**
     * 获取个人中心菜单
     * @return HashMap<String, Object>
     */
    @Override
    public CommonResult<HashMap<String, Object>> getMenuUser() {
        return CommonResult.success(systemGroupDataService.getMenuUser());
    }
}
