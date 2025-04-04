package com.nbug.admin.manager;

import cn.hutool.core.util.ObjectUtil;
import com.nbug.common.exception.XlwebException;
import com.nbug.common.utils.XlwebUtil;
import com.nbug.common.vo.LoginUserVo;
import com.nbug.service.service.impl.UserDetailServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * 自定义验证（admin登录）
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private UserDetailServiceImpl userDetailsService;

    public CustomAuthenticationProvider(UserDetailServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    private final UserDetailServiceImpl userDetailsService = new UserDetailServiceImpl();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        //以下自定义方法，判断是否登录成功
        LoginUserVo userDetails = (LoginUserVo) userDetailsService.loadUserByUsername(name);
        if (ObjectUtil.isNull(userDetails)) {
            throw new XlwebException("用户名不存在");
        }
        // base64加密获取真正密码
        String encryptPassword = XlwebUtil.encryptPassword(password, name);
        if (!userDetails.getUser().getPwd().equals(encryptPassword)) {
            throw new XlwebException("账号或者密码不正确");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        //确保authentication能转成该类
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
