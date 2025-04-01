package com.nbug.module.infra.api.wechat;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.token.WeChatOauthToken;
import com.nbug.mico.common.vo.CreateOrderRequestVo;
import com.nbug.mico.common.vo.CreateOrderResponseVo;
import com.nbug.mico.common.vo.WeChatAuthorizeLoginUserInfoVo;
import com.nbug.mico.common.vo.WeChatMiniAuthorizeVo;
import com.nbug.mico.common.vo.WxRefundResponseVo;
import com.nbug.mico.common.vo.WxRefundVo;
import com.nbug.module.infra.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 微信")
public interface WechatNewApi {

    String PREFIX = ApiConstants.PREFIX + "/wechatNew";

    @PostMapping(PREFIX + "/payUnifiedorder")
    @Operation(summary = "微信预下单接口(统一下单)")
    @Parameter(name = "unifiedorderVo", description = "统一下单参数", required = true)
    public CommonResult<CreateOrderResponseVo> payUnifiedorder(@Validated @RequestBody CreateOrderRequestVo unifiedorderVo);

    @PostMapping(PREFIX + "/payRefund")
    @Operation(summary = "微信退款接口")
    @Parameters({
            @Parameter(name = "wxRefundVo", description = "微信退款参数", required = true),
            @Parameter(name = "path", description = "退款路径", required = true)
    })
    public CommonResult<WxRefundResponseVo> payRefund(@Validated @RequestBody WxRefundVo wxRefundVo,
                                                      @RequestParam String path);

    @GetMapping(PREFIX + "/miniAuthCode")
    @Operation(summary = "微信小程序授权登录")
    @Parameter(name = "code", description = "微信授权码", required = true)
    public CommonResult<WeChatMiniAuthorizeVo> miniAuthCode(@RequestParam String code);

    @GetMapping(PREFIX + "/getOauth2AccessToken")
    @Operation(summary = "微信授权登录")
    @Parameter(name = "code", description = "微信授权码", required = true)
    public CommonResult<WeChatOauthToken> getOauth2AccessToken(@RequestParam String code);

    @GetMapping(PREFIX + "/getSnsUserInfo")
    @Operation(summary = "获取开放平台用户信息")
    @Parameters({
            @Parameter(name = "accessToken", description = "accessToken", required = true),
            @Parameter(name = "openid", description = "openid", required = true)
    })
    public CommonResult<WeChatAuthorizeLoginUserInfoVo> getSnsUserInfo(@RequestParam String accessToken,
                                                                       @RequestParam String openid);
}
