package com.nbug.module.user.controller.app;


import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.LoginMobileRequest;
import com.nbug.mico.common.request.LoginRequest;
import com.nbug.mico.common.response.LoginResponse;
import com.nbug.module.infra.api.sms.SmsApi;
import com.nbug.module.user.service.LoginService;
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

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登陆 前端控制器
 
 */
@Slf4j
@RestController("FrontLoginController")
@RequestMapping("/user/login")
@Tag(name = "应用后台 - 用户 -- 登录注册")
public class LoginController {

    @Autowired
    private SmsApi smsApi;

    @Autowired
    private LoginService loginService;

    /**
     * 手机号登录接口
     */
    @Operation(summary = "手机号登录接口")
    @RequestMapping(value = "/mobile", method = RequestMethod.POST)
    public CommonResult<LoginResponse> phoneLogin(@RequestBody @Validated LoginMobileRequest loginRequest) {
        return CommonResult.success(loginService.phoneLogin(loginRequest));
    }

    /**
     * 账号密码登录
     */
    @Operation(summary = "账号密码登录")
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public CommonResult<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        return CommonResult.success(loginService.login(loginRequest));
    }


    /**
     * 退出登录
     */
    @Operation(summary = "退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public CommonResult<String> loginOut(HttpServletRequest request){
        loginService.loginOut(request);
        return CommonResult.success("success");
    }

    /**
     * 发送短信登录验证码
     * @param phone 手机号码
     * @return 发送是否成功
     */
    @Operation(summary = "发送短信登录验证码")
    @RequestMapping(value = "/sendCode", method = RequestMethod.POST)
    @Parameters({
            @Parameter(name="phone", description="手机号码", required = true)
    })
    public CommonResult<Object> sendCode(@RequestParam String phone){
        smsApi.sendCommonCode(phone).getCheckedData();
        return CommonResult.success("发送成功");
    }
}



