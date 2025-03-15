package com.nbug.module.user.api.userRecharge;

import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserRechargeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class userRechargeApiImpl implements UserRechargeApi {

    @Resource
    private UserRechargeService userRechargeService;

    /**
     * 根据对象查询订单
     * @param userRecharge
     * @return
     */
    @Override
    public CommonResult<UserRecharge> getInfoByEntity(UserRecharge userRecharge) {
        return success(userRechargeService.getInfoByEntity(userRecharge));
    }

    @Override
    public CommonResult<Boolean> updateById(UserRecharge userRecharge) {
        return success(userRechargeService.updateById(userRecharge));
    }
}
