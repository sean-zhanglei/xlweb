package com.nbug.module.user.service;


import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.request.LoginMobileRequest;
import com.nbug.mico.common.request.LoginRequest;
import com.nbug.mico.common.response.LoginResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * 移动端登录服务类

 */
public interface LoginService {

    /**
     * 账号密码登录
     * @return LoginResponse
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 手机号验证码登录
     */
    LoginResponse phoneLogin(LoginMobileRequest loginRequest);

    /**
     * 老绑定分销关系
     * @param user User 用户user类
     * @param spreadUid Integer 推广人id
     * @return Boolean
     */
    Boolean bindSpread(User user, Integer spreadUid);

    /**
     * 推出登录
     * @param request HttpServletRequest
     */
    void loginOut(HttpServletRequest request);
}
