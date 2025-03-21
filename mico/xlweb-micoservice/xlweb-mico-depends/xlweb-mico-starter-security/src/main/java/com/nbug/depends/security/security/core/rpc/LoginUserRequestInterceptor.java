package com.nbug.depends.security.security.core.rpc;

import com.nbug.depends.security.security.core.util.SecurityFrameworkUtils;
import com.nbug.mico.common.json.JsonUtils;
import com.nbug.mico.common.vo.LoginUserVo;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * LoginUser 的 RequestInterceptor 实现类：Feign 请求时，将 {@link LoginUserVo} 设置到 header 中，继续透传给被调用的服务
 *
 * @author NBUG
 */
@Slf4j
public class LoginUserRequestInterceptor implements RequestInterceptor {

    @Override
    @SneakyThrows
    public void apply(RequestTemplate requestTemplate) {
        LoginUserVo user = SecurityFrameworkUtils.getLoginUser();
        if (user == null) {
            return;
        }
        try {
            //  feign 请求时，缩小header 的大小，避免 header 过大
            user.setPermissions(new ArrayList<>());// 去掉授权信息
            String userStr = JsonUtils.toJsonString(user);
            userStr = URLEncoder.encode(userStr, StandardCharsets.UTF_8.name()); // 编码，避免中文乱码
            // feign 请求时，将 LoginUserVo 设置到 header 中，继续透传给被调用的服务
            // 缩小header 的大小，避免 header 过大
            requestTemplate.header(SecurityFrameworkUtils.LOGIN_USER_HEADER, userStr);
        } catch (Exception ex) {
            log.error("[apply][序列化 LoginUser({}) 发生异常]", user, ex);
            throw ex;
        }
    }

}
