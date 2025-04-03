package com.nbug.depends.websockets.core.util;

import com.nbug.mico.common.vo.LoginUserVo;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 专属于 web 包的工具类
 *
 * @author NBUG
 */
public class WebSocketFrameworkUtils {

    public static final String ATTRIBUTE_LOGIN_USER = "LOGIN_USER";

    /**
     * 设置当前用户
     *
     * @param loginUser 登录用户
     * @param attributes Session
     */
    public static void setLoginUser(LoginUserVo loginUser, Map<String, Object> attributes) {
        attributes.put(ATTRIBUTE_LOGIN_USER, loginUser);
    }

    /**
     * 获取当前用户
     *
     * @return 当前用户
     */
    public static LoginUserVo getLoginUser(WebSocketSession session) {
        return (LoginUserVo) session.getAttributes().get(ATTRIBUTE_LOGIN_USER);
    }

    /**
     * 获得当前用户的编号
     *
     * @return 用户编号
     */
    public static Long getLoginUserId(WebSocketSession session) {
        LoginUserVo loginUser = getLoginUser(session);
        return loginUser != null ? Long.valueOf(loginUser.getUser().getId()) : null;
    }

    /**
     * 获得当前用户的类型
     *
     * @return 用户编号
     */
    public static Integer getLoginUserType(WebSocketSession session) {
        LoginUserVo loginUser = getLoginUser(session);
        return loginUser != null ? loginUser.getUserType() : null;
    }

    /**
     * 获得当前用户的租户编号
     *
     * @param session Session
     * @return 租户编号
     */
    public static Long getTenantId(WebSocketSession session) {
        LoginUserVo loginUser = getLoginUser(session);
        return loginUser != null ? loginUser.getTenantId() : null;
    }

}
