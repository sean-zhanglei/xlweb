package com.nbug.module.user.api.userDetail;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.LoginUserVo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserDetailApiImpl implements UserDetailApi {

    @Resource
    private UserDetailsService userDetailsService;


    /**
     * 获取admin 用户信息
     * @return UserAddress
     */
    @Override
    public CommonResult<LoginUserVo> loadUserByUsername(String username) {
        return success((LoginUserVo)userDetailsService.loadUserByUsername(username));
    }

}
