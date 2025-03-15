package com.nbug.module.user.api.user;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserApiImpl implements UserApi {

    @Resource
    private UserService userService;

    /**
     * 检测手机验证码key
     *
     * @param phone String 手机号
     * @return String
     */
    @Override
    public CommonResult<String> getValidateCodeRedisKey(String phone) {
        return success(userService.getValidateCodeRedisKey(phone));
    }

}
