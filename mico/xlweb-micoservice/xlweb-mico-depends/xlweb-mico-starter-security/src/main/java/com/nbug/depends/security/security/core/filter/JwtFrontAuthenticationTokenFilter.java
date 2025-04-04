package com.nbug.depends.security.security.core.filter;

import cn.hutool.core.util.ObjectUtil;
import com.nbug.depends.web.web.core.util.WebFrameworkUtils;
import com.nbug.mico.common.enums.UserTypeEnum;
import com.nbug.mico.common.token.FrontTokenComponent;
import com.nbug.mico.common.vo.LoginUserVo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**

 * token过滤器 验证token有效性
 */
@Component
public class JwtFrontAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private FrontTokenComponent frontTokenComponent;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LoginUserVo loginUser = frontTokenComponent.getLoginUser(request);
        if (ObjectUtil.isNotNull(loginUser)) {
            frontTokenComponent.verifyToken(loginUser);
            boolean result = frontTokenComponent.check(frontTokenComponent.getToken(request), request);
            if(! result){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 将authentication信息放入到上下文对象中
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                // 额外设置到 request 中，用于 ApiAccessLogFilter 可以获取到用户编号；
                // 原因是，Spring Security 的 Filter 在 ApiAccessLogFilter 后面，在它记录访问日志时，线上上下文已经没有用户编号等信息
                WebFrameworkUtils.setLoginUserId(request, Long.valueOf(loginUser.getFrontUser().getUid()));
                WebFrameworkUtils.setLoginUserType(request, UserTypeEnum.MEMBER.getValue());
            }
        }
        filterChain.doFilter(request, response);
    }
}
