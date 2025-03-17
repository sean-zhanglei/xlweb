package com.nbug.module.system.api.systemGroupData;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.response.SystemGroupDataOrderStatusPicResponse;
import com.nbug.mico.common.response.UserRechargeItemResponse;
import com.nbug.mico.common.response.UserSpreadBannerResponse;
import com.nbug.mico.common.vo.SystemGroupDataRechargeConfigVo;
import com.nbug.mico.common.vo.SystemGroupDataSignConfigVo;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 组合数据")
public interface SystemGroupDataApi {

    String PREFIX = ApiConstants.PREFIX + "/systemGroupData";

    @GetMapping(PREFIX + "/getListMapByGid")
    @Operation(summary = "根据组合数据ID，获得组合数据 Map")
    @Parameter(name = "gid", description = "组合数据ID", required = true)
    public CommonResult<List<HashMap<String, Object>>> getListMapByGid(@RequestParam Integer gid);


    @GetMapping(PREFIX + "/getGroupDataOrderStatusPicListByGid")
    @Operation(summary = "根据组合数据ID，获得组合数据订单状态图片")
    @Parameter(name = "gid", description = "组合数据ID", required = true)
    public CommonResult<List<SystemGroupDataOrderStatusPicResponse>> getGroupDataOrderStatusPicListByGid(@RequestParam Integer gid);


    @GetMapping(PREFIX + "/getGroupDataSignConfigListByGid")
    @Operation(summary = "根据组合数据ID，获得组合数据签到记录")
    @Parameter(name = "gid", description = "组合数据ID", required = true)
    public CommonResult<List<SystemGroupDataSignConfigVo>> getGroupDataSignConfigListByGid(@RequestParam Integer gid);

    @GetMapping(PREFIX + "/getGroupDataSpreadBannerListByGid")
    @Operation(summary = "根据组合数据ID，获得组合数据用户地址")
    @Parameter(name = "gid", description = "组合数据ID", required = true)
    public CommonResult<List<UserSpreadBannerResponse>> getGroupDataSpreadBannerListByGid(@RequestParam Integer gid);

    @GetMapping(PREFIX + "/getGroupDataRechargeFrontListByGid")
    @Operation(summary = "根据组合数据ID，获得组合数据充值套餐")
    @Parameter(name = "gid", description = "组合数据ID", required = true)
    public CommonResult<List<UserRechargeItemResponse>> getGroupDataRechargeFrontListByGid(@RequestParam Integer gid);


    @GetMapping(PREFIX + "/getGroupDataRechargeConfig")
    @Operation(summary = "根据组合数据ID，获得组合数据用户充值")
    @Parameter(name = "groupDataId", description = "组合数据ID", required = true)
    public CommonResult<SystemGroupDataRechargeConfigVo> getGroupDataRechargeConfig(@RequestParam Integer groupDataId);

    @GetMapping(PREFIX + "/getMenuUser")
    @Operation(summary = "获取个人中心菜单")
    public CommonResult<HashMap<String, Object>> getMenuUser();
}
