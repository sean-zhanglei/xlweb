package com.nbug.module.user.api.userBill;

import com.nbug.mico.common.model.user.UserBill;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserBillService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class userBillApiImpl implements UserBillApi {

    @Resource
    private UserBillService userBillService;

    @Override
    public CommonResult<Boolean> save(UserBill userBill) {
        return success(userBillService.save(userBill));
    }
}
