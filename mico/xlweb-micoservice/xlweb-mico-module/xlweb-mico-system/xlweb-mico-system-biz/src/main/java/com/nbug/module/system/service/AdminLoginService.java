package com.nbug.module.system.service;


import com.nbug.mico.common.request.SystemAdminLoginRequest;
import com.nbug.mico.common.response.MenusResponse;
import com.nbug.mico.common.response.SystemAdminResponse;
import com.nbug.mico.common.response.SystemLoginResponse;

import java.util.List;
import java.util.Map;

/**
 * 管理端登录服务

 */
public interface AdminLoginService {

    /**
     * PC登录
     */
    SystemLoginResponse login(SystemAdminLoginRequest request, String ip);

    /**
     * 用户登出
     */
    Boolean logout();

    /**
     * 获取登录页图片
     * @return Map
     */
    Map<String, Object> getLoginPic();

    /**
     * 获取管理员可访问目录
     * @return List<MenusResponse>
     */
    List<MenusResponse> getMenus();

    /**
     * 根据Token获取对应用户信息
     */
    SystemAdminResponse getInfoByToken();
}
