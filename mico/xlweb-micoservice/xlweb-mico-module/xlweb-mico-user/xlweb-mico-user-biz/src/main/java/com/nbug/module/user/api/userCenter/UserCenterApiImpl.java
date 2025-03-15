package com.nbug.module.user.api.userCenter;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.RegisterThirdUserRequest;
import com.nbug.mico.common.request.WxBindingPhoneRequest;
import com.nbug.mico.common.response.LoginResponse;
import com.nbug.module.user.service.UserCenterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserCenterApiImpl implements UserCenterApi {

    @Resource
    private UserCenterService userCenterService;

    /**
     * 小程序获取授权logo
     * @return String
     */
    @Override
    public CommonResult<String> getLogo() {
        return success(userCenterService.getLogo());
    }

    /**
     * 微信注册绑定手机号
     * @param request 请求参数
     * @return 登录信息
     */
    @Override
    public CommonResult<LoginResponse> registerBindingPhone(WxBindingPhoneRequest request) {
        return success(userCenterService.registerBindingPhone(request));
    }

    /**
     * 通过微信code登录
     */
    @Override
    public CommonResult<LoginResponse> weChatAuthorizeLogin(String code, Integer spreadUid) {
        return success(userCenterService.weChatAuthorizeLogin(code, spreadUid));
    }

    /**
     * 微信登录小程序授权登录
     * @param code code
     * @param request 用户参数
     * @return LoginResponse
     */
    @Override
    public CommonResult<LoginResponse> weChatAuthorizeProgramLogin(String code, RegisterThirdUserRequest request) {
        return success(userCenterService.weChatAuthorizeProgramLogin(code, request));
    }
}
