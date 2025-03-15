package com.nbug.module.user.api.userLevel;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserLevelService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class userLevelApiImpl implements UserLevelApi {

    @Resource
    private UserLevelService userLevelService;

    /**
     * 经验降级
     * @param user 用户
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> downLevel(User user) {
        return success(userLevelService.downLevel(user));
    }


    /**
     * 经验升级
     * @param user 用户
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> upLevel(User user) {
        return success(userLevelService.upLevel(user));
    }

    /**
     * 删除（通过系统等级id）
     * @param levelId 系统等级id
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> deleteByLevelId(Integer levelId) {
        return success(userLevelService.deleteByLevelId(levelId));
    }

}
