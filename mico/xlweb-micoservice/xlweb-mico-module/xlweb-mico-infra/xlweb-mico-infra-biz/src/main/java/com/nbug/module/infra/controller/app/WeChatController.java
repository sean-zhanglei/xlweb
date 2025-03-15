package com.nbug.module.infra.controller.app;

import com.nbug.mico.common.model.wechat.TemplateMessage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.RegisterThirdUserRequest;
import com.nbug.mico.common.request.WxBindingPhoneRequest;
import com.nbug.mico.common.response.LoginResponse;
import com.nbug.mico.common.response.WeChatJsSdkConfigResponse;
import com.nbug.module.infra.service.sms.SystemNotificationService;
import com.nbug.module.infra.service.wechat.WechatNewService;
import com.nbug.module.user.api.userCenter.UserCenterApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 微信缓存表 前端控制器

 */
@Slf4j
@RestController("WeChatFrontController")
@RequestMapping("api/front/infra/wechat")
@Tag(name = "微信 -- 开放平台")
public class WeChatController {

    @Autowired
    private UserCenterApi userCenterApi;

    @Autowired
    private WechatNewService wechatNewService;

    @Autowired
    private SystemNotificationService systemNotificationService;

    /**
     * 通过微信code登录
     */
    @Operation(summary= "微信登录公共号授权登录")
    @RequestMapping(value = "/authorize/login", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "spread_spid", description = "推荐人id"),
            @Parameter(name = "code", description = "code码", required = true)
    })
    public CommonResult<LoginResponse> login(@RequestParam(value = "spread_spid", defaultValue = "0", required = false) Integer spreadUid,
                                             @RequestParam(value = "code") String code){
        return CommonResult.success(userCenterApi.weChatAuthorizeLogin(code, spreadUid).getCheckedData());
    }

    /**
     * 微信登录小程序授权登录
     */
    @Operation(summary= "微信登录小程序授权登录")
    @RequestMapping(value = "/authorize/program/login", method = RequestMethod.POST)
    public CommonResult<LoginResponse> programLogin(@RequestParam String code, @RequestBody @Validated RegisterThirdUserRequest request){
        return CommonResult.success(userCenterApi.weChatAuthorizeProgramLogin(code, request).getCheckedData());
    }

    /**
     * 微信注册绑定手机号
     */
    @Operation(summary= "微信注册绑定手机号")
    @RequestMapping(value = "/register/binding/phone", method = RequestMethod.POST)
    public CommonResult<LoginResponse> registerBindingPhone(@RequestBody @Validated WxBindingPhoneRequest request){
        return CommonResult.success(userCenterApi.registerBindingPhone(request).getCheckedData());
    }

    /**
     * 获取微信公众号js配置
     */
    @Operation(summary= "获取微信公众号js配置")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    @Parameter(name = "url", description = "页面地址url")
    public CommonResult<WeChatJsSdkConfigResponse> configJs(@RequestParam(value = "url") String url){
        return CommonResult.success(wechatNewService.getJsSdkConfig(url));
    }

    /**
     * 小程序获取授权logo
     */
    @Operation(summary= "小程序获取授权logo")
    @RequestMapping(value = "/getLogo", method = RequestMethod.GET)
    public CommonResult<Map<String, String>> getLogo(){
        Map<String, String> map = new HashMap<>();
        map.put("logoUrl", userCenterApi.getLogo().getCheckedData());
        return CommonResult.success(map);
    }

    /**
     * 订阅消息模板列表
     */
    @Operation(summary= "订阅消息模板列表")
    @RequestMapping(value = "/program/my/temp/list", method = RequestMethod.GET)
    @Parameter(name = "type", description = "支付之前：beforePay|支付成功：afterPay|申请退款：refundApply|充值之前：beforeRecharge|创建砍价：createBargain|参与拼团：pink|取消拼团：cancelPink")
    public CommonResult<List<TemplateMessage>> programMyTempList(@RequestParam(name = "type") String type){
        return CommonResult.success(systemNotificationService.getMiniTempList(type));
    }
}



