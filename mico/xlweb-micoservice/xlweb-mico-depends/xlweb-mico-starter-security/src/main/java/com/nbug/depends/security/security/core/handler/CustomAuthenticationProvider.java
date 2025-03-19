package com.nbug.depends.security.security.core.handler;

import cn.hutool.core.util.ObjectUtil;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.utils.XlwebUtil;
import com.nbug.mico.common.vo.LoginUserVo;
import com.nbug.module.user.api.userDetail.UserDetailApi;
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


    private UserDetailApi userDetailApi;

    public CustomAuthenticationProvider(UserDetailApi userDetailApi) {
        this.userDetailApi = userDetailApi;
    }

//    private final UserDetailServiceImpl userDetailsService = new UserDetailServiceImpl();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        //以下自定义方法，判断是否登录成功
        LoginUserVo userDetails = (LoginUserVo) userDetailApi.loadUserByUsername(name).getCheckedData();
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
