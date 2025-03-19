package com.nbug.depends.security.security.core.util;

import com.nbug.mico.common.vo.LoginUserVo;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全服务工具类
 *
 * @author NBUG
 */
public class SecurityFrameworkUtils {

    public static final String LOGIN_USER_HEADER = "login-user";

    private SecurityFrameworkUtils() {}

    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    @Nullable
    public static LoginUserVo getLoginUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            return null;
        }
        return authentication.getPrincipal() instanceof LoginUserVo ? (LoginUserVo) authentication.getPrincipal() : null;
    }

    /**
     * 获得当前用户的编号，从上下文中
     *
     * @return 用户编号
     */
    @Nullable
    public static Long getLoginUserId() {
        LoginUserVo loginUser = getLoginUser();
        return loginUser != null ? Long.valueOf(loginUser.getUser().getId()) : null;
    }
}
