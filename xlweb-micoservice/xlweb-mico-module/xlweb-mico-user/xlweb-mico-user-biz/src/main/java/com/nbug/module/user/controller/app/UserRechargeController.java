package com.nbug.module.user.controller.app;

import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserRechargeRequest;
import com.nbug.mico.common.response.OrderPayResultResponse;
import com.nbug.mico.common.response.UserRechargeBillRecordResponse;
import com.nbug.mico.common.response.UserRechargeFrontResponse;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.module.user.service.UserCenterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户 -- 充值

 */
@Slf4j
@RestController("UserRechargeController")
@RequestMapping("api/front/recharge")
@Tag(name = "用户 -- 充值")
public class UserRechargeController {
    @Autowired
    private UserCenterService userCenterService;

    /**
     * 充值额度选择
     */
    @Operation(summary = "充值额度选择")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<UserRechargeFrontResponse> getRechargeConfig() {
        return CommonResult.success(userCenterService.getRechargeConfig());
    }

    /**
     * 小程序充值
     */
    @Operation(summary = "小程序充值")
    @RequestMapping(value = "/routine", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> routineRecharge(HttpServletRequest httpServletRequest, @RequestBody @Validated UserRechargeRequest request) {
        request.setFromType(Constants.PAY_TYPE_WE_CHAT_FROM_PROGRAM);
        request.setClientIp(XlwebUtil.getClientIp(httpServletRequest));
        OrderPayResultResponse recharge = userCenterService.recharge(request);
        Map<String, Object> map = new HashMap<>();
        map.put("data", recharge);
        map.put("type", request.getFromType());
        return CommonResult.success(map);
    }

    /**
     * 公众号充值
     */
    @Operation(summary = "公众号充值")
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public CommonResult<OrderPayResultResponse> weChatRecharge(HttpServletRequest httpServletRequest, @RequestBody @Validated UserRechargeRequest request) {
        request.setClientIp(XlwebUtil.getClientIp(httpServletRequest));
        return CommonResult.success(userCenterService.recharge(request));
    }

    /**
     * 佣金转入余额
     */
    @Operation(summary = "佣金转入余额")
    @RequestMapping(value = "/transferIn", method = RequestMethod.POST)
    public CommonResult<Boolean> transferIn(@RequestParam(name = "price") BigDecimal price) {
        return CommonResult.success(userCenterService.transferIn(price));
    }

    /**
     * 用户账单记录
     */
    @Operation(summary = "用户账单记录")
    @RequestMapping(value = "/bill/record", method = RequestMethod.GET)
    @Parameter(name = "type", description = "记录类型：all-全部，expenditure-支出，income-收入", required = true)
    public CommonResult<CommonPage<UserRechargeBillRecordResponse>> billRecord(@RequestParam(name = "type") String type, @ModelAttribute PageParamRequest pageRequest) {
        return CommonResult.success(userCenterService.nowMoneyBillRecord(type, pageRequest));
    }
}



