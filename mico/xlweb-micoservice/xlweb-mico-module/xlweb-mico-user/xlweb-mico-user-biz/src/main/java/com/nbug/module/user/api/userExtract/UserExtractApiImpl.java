package com.nbug.module.user.api.userExtract;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.response.UserExtractResponse;
import com.nbug.module.user.service.UserExtractService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserExtractApiImpl implements UserExtractApi {

    @Resource
    private UserExtractService userExtractService;

    @Override
    public CommonResult<UserExtractResponse> getUserExtractByUserId(Integer userId) {
        return success(userExtractService.getUserExtractByUserId(userId));
    }
}
