package com.nbug.module.user.api.userCenter;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.RegisterThirdUserRequest;
import com.nbug.mico.common.request.WxBindingPhoneRequest;
import com.nbug.mico.common.response.LoginResponse;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户中心")
public interface UserCenterApi {

    String PREFIX = ApiConstants.PREFIX + "/userCenter";

    @GetMapping(PREFIX + "/getLogo")
    @Operation(summary = "获取小程序logo")
    public CommonResult<String> getLogo();

    @PostMapping(PREFIX + "/registerBindingPhone")
    @Operation(summary = "微信注册绑定手机号")
    @Parameter(name = "request", description = "微信绑定手机号请求对象", required = true)
    public CommonResult<LoginResponse> registerBindingPhone(WxBindingPhoneRequest request);

    @PostMapping(PREFIX + "/weChatAuthorizeLogin")
    @Operation(summary = "通过微信code登录")
    @Parameters({
            @Parameter(name = "code", description = "code码", required = true),
            @Parameter(name = "spreadUid", description = "推荐人id")
    })
    public CommonResult<LoginResponse> weChatAuthorizeLogin(String code, Integer spreadUid);


    @PostMapping(PREFIX + "/weChatAuthorizeProgramLogin")
    @Operation(summary = "微信登录小程序授权登录")
    @Parameters({
            @Parameter(name = "code", description = "code码", required = true),
            @Parameter(name = "request", description = "微信绑定手机号请求对象", required = true)
    })
    public CommonResult<LoginResponse> weChatAuthorizeProgramLogin(String code, RegisterThirdUserRequest request);
}
