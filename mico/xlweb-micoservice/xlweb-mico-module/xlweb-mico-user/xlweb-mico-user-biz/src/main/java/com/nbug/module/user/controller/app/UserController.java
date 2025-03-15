package com.nbug.module.user.controller.app;


import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.system.SystemUserLevel;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.model.user.UserIntegralRecord;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.PasswordRequest;
import com.nbug.mico.common.request.UserBindingPhoneUpdateRequest;
import com.nbug.mico.common.request.UserEditRequest;
import com.nbug.mico.common.request.UserExtractRequest;
import com.nbug.mico.common.request.UserSpreadPeopleRequest;
import com.nbug.mico.common.response.IntegralUserResponse;
import com.nbug.mico.common.response.SpreadCommissionDetailResponse;
import com.nbug.mico.common.response.UserBalanceResponse;
import com.nbug.mico.common.response.UserCenterResponse;
import com.nbug.mico.common.response.UserCommissionResponse;
import com.nbug.mico.common.response.UserExtractCashResponse;
import com.nbug.mico.common.response.UserExtractRecordResponse;
import com.nbug.mico.common.response.UserSpreadBannerResponse;
import com.nbug.mico.common.response.UserSpreadOrderResponse;
import com.nbug.mico.common.response.UserSpreadPeopleItemResponse;
import com.nbug.mico.common.response.UserSpreadPeopleResponse;
import com.nbug.module.system.api.systemGroupData.SystemGroupDataApi;
import com.nbug.module.user.service.UserCenterService;
import com.nbug.module.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;

/**
 * 用户 -- 用户中心

 */
@Slf4j
@RestController("FrontUserController")
@RequestMapping("api/front/user")
@Tag(name = "用户 -- 用户中心")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemGroupDataApi systemGroupDataApi;

    @Autowired
    private UserCenterService userCenterService;

    /**
     * 修改密码
     */
    @Operation(summary = "手机号修改密码")
    @RequestMapping(value = "/register/reset", method = RequestMethod.POST)
    public CommonResult<Boolean> password(@RequestBody @Validated PasswordRequest request) {
        return CommonResult.success(userService.password(request));
    }

    /**
     * 修改个人资料
     */
    @Operation(summary = "修改个人资料")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public CommonResult<Object> personInfo(@RequestBody @Validated UserEditRequest request) {
        if (userService.editUser(request)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 个人中心-用户信息
     */
    @Operation(summary = "个人中心-用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<UserCenterResponse> getUserCenter() {
        return CommonResult.success(userService.getUserCenter());
    }

    /**
     * 换绑手机号校验
     */
    @Operation(summary = "换绑手机号校验")
    @RequestMapping(value = "/update/binding/verify", method = RequestMethod.POST)
    public CommonResult<Boolean> updatePhoneVerify(@RequestBody @Validated UserBindingPhoneUpdateRequest request) {
        return CommonResult.success(userService.updatePhoneVerify(request));
    }

    /**
     * 绑定手机号
     */
    @Operation(summary = "换绑手机号")
    @RequestMapping(value = "/update/binding", method = RequestMethod.POST)
    public CommonResult<Boolean> updatePhone(@RequestBody @Validated UserBindingPhoneUpdateRequest request) {
        return CommonResult.success(userService.updatePhone(request));
    }

    /**
     * 用户中心菜单
     */
    @Operation(summary = "获取个人中心菜单")
    @RequestMapping(value = "/menu/user", method = RequestMethod.GET)
    public CommonResult<HashMap<String, Object>> getMenuUser() {
        return CommonResult.success(systemGroupDataApi.getMenuUser()).getCheckedData();
    }

    /**
     * 推广数据接口(昨天的佣金 累计提现金额 当前佣金)
     */
    @Operation(summary = "推广数据接口(昨天的佣金 累计提现金额 当前佣金)")
    @RequestMapping(value = "/commission", method = RequestMethod.GET)
    public CommonResult<UserCommissionResponse> getCommission() {
        return CommonResult.success(userCenterService.getCommission());
    }

    /**
     * 推广佣金明细
     */
    @Operation(summary = "推广佣金明细")
    @RequestMapping(value = "/spread/commission/detail", method = RequestMethod.GET)
    public CommonResult<CommonPage<SpreadCommissionDetailResponse>> getSpreadCommissionDetail(@Validated PageParamRequest pageParamRequest) {
        PageInfo<SpreadCommissionDetailResponse> commissionDetail = userCenterService.getSpreadCommissionDetail(pageParamRequest);
        return CommonResult.success(CommonPage.restPage(commissionDetail));
    }

    /**
     * 推广佣金/提现总和
     */
    @Operation(summary = "推广佣金/提现总和")
    @RequestMapping(value = "/spread/count/{type}", method = RequestMethod.GET)
    @Parameter(name = "type", description = "类型 佣金类型3=佣金,4=提现")
    public CommonResult<Map<String, BigDecimal>> getSpreadCountByType(@PathVariable Integer type) {
        Map<String, BigDecimal> map = new HashMap<>();
        map.put("count", userCenterService.getSpreadCountByType(type));
        return CommonResult.success(map);
    }

    /**
     * 提现申请
     */
    @Operation(summary = "提现申请")
    @RequestMapping(value = "/extract/cash", method = RequestMethod.POST)
    public CommonResult<Boolean> extractCash(@RequestBody @Validated UserExtractRequest request) {
        return CommonResult.success(userCenterService.extractCash(request));
    }

    /**
     * 提现记录
     */
    @Operation(summary = "提现记录")
    @RequestMapping(value = "/extract/record", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserExtractRecordResponse>> getExtractRecord(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userCenterService.getExtractRecord(pageParamRequest)));
    }

    /**
     * 提现用户信息
     */
    @Operation(summary = "提现用户信息")
    @RequestMapping(value = "/extract/user", method = RequestMethod.GET)
    public CommonResult<UserExtractCashResponse> getExtractUser() {
        return CommonResult.success(userCenterService.getExtractUser());
    }

    /**
     * 提现银行
     */
    @Operation(summary = "提现银行/提现最低金额")
    @RequestMapping(value = "/extract/bank", method = RequestMethod.GET)
    public CommonResult<List<String>> getExtractBank() {
        return CommonResult.success(userCenterService.getExtractBank());
    }

    /**
     * 会员等级列表
     */
    @Operation(summary = "会员等级列表")
    @RequestMapping(value = "/level/grade", method = RequestMethod.GET)
    public CommonResult<List<SystemUserLevel>> getUserLevelList() {
        return CommonResult.success(userCenterService.getUserLevelList());
    }

    /**
     * 推广人统计
     */
    @Operation(summary = "推广人统计")
    @RequestMapping(value = "/spread/people/count", method = RequestMethod.GET)
    public CommonResult<UserSpreadPeopleResponse>  getSpreadPeopleCount() {
        return CommonResult.success(userCenterService.getSpreadPeopleCount());
    }

    /**
     * 推广人列表
     */
    @Operation(summary = "推广人列表")
    @RequestMapping(value = "/spread/people", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserSpreadPeopleItemResponse>> getSpreadPeopleList(@Validated UserSpreadPeopleRequest request, @Validated PageParamRequest pageParamRequest) {
        List<UserSpreadPeopleItemResponse> spreadPeopleList = userCenterService.getSpreadPeopleList(request, pageParamRequest);
        CommonPage<UserSpreadPeopleItemResponse> commonPage = CommonPage.restPage(spreadPeopleList);
        return CommonResult.success(commonPage);
    }

    /**
     * 用户积分信息
     */
    @Operation(summary = "用户积分信息")
    @RequestMapping(value = "/integral/user", method = RequestMethod.GET)
    public CommonResult<IntegralUserResponse> getIntegralUser() {
        return CommonResult.success(userCenterService.getIntegralUser());
    }

    /**
     * 积分记录
     */
    @Operation(summary = "积分记录")
    @RequestMapping(value = "/integral/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserIntegralRecord>> getIntegralList(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userCenterService.getUserIntegralRecordList(pageParamRequest)));
    }

    /**
     * 经验记录
     */
    @Operation(summary = "经验记录")
    @RequestMapping(value = "/expList", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserExperienceRecord>> getExperienceList(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userCenterService.getUserExperienceList(pageParamRequest)));
    }

    /**
     * 用户资金统计
     */
    @Operation(summary = "用户资金统计")
    @RequestMapping(value = "/balance", method = RequestMethod.GET)
    public CommonResult<UserBalanceResponse> getUserBalance() {
        return CommonResult.success(userCenterService.getUserBalance());
    }

    /**
     * 推广订单
     */
    @Operation(summary = "推广订单")
    @RequestMapping(value = "/spread/order", method = RequestMethod.GET)
    public CommonResult<UserSpreadOrderResponse> getSpreadOrder(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(userCenterService.getSpreadOrder(pageParamRequest));
    }

    /**
     * 推广人排行
     * @return List<User>
     */
    @Operation(summary = "推广人排行")
    @RequestMapping(value = "/rank", method = RequestMethod.GET)
    public CommonResult<List<User>> getTopSpreadPeopleListByDate(@RequestParam(required = false) String type, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(userCenterService.getTopSpreadPeopleListByDate(type, pageParamRequest));
    }

    /**
     * 佣金排行
     * @return 优惠券集合
     */
    @Operation(summary = "佣金排行")
    @RequestMapping(value = "/brokerage_rank", method = RequestMethod.GET)
    public CommonResult<List<User>> getTopBrokerageListByDate(@RequestParam String type, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(userCenterService.getTopBrokerageListByDate(type, pageParamRequest));
    }

    /**
     * 当前用户在佣金排行第几名
     */
    @Operation(summary = "当前用户在佣金排行第几名")
    @RequestMapping(value = "/brokerageRankNumber", method = RequestMethod.GET)
    public CommonResult<Integer> getNumberByTop(@RequestParam String type) {
        return CommonResult.success(userCenterService.getNumberByTop(type));
    }

    /**
     * 海报背景图
     */
    @Operation(summary = "推广海报图")
    @RequestMapping(value = "/spread/banner", method = RequestMethod.GET)
    public CommonResult<List<UserSpreadBannerResponse>>  getSpreadBannerList() {
        return CommonResult.success(userCenterService.getSpreadBannerList());
    }

    /**
     * 绑定推广关系（登录状态）
     * @param spreadPid 推广id
     * @return 绑定结果
     */
    @Operation(summary = "绑定推广关系（登录状态）")
    @RequestMapping(value = "/bindSpread", method = RequestMethod.GET)
    public CommonResult<Boolean> bindsSpread(Integer spreadPid) {
        userService.bindSpread(spreadPid);
        return CommonResult.success(true);
    }
}



