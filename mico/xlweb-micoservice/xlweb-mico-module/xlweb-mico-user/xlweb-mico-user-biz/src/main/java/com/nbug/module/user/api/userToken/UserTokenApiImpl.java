package com.nbug.module.user.api.userToken;

import com.nbug.mico.common.model.user.UserToken;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserTokenService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserTokenApiImpl implements UserTokenApi {

    @Resource
    private UserTokenService userTokenService;

    /**
     * 获取UserToken
     * @param userId
     * @param type
     * @return
     */
    @Override
    public CommonResult<UserToken> getTokenByUserId(Integer userId, int type) {
        return success(userTokenService.getTokenByUserId(userId, type));
    }

}
