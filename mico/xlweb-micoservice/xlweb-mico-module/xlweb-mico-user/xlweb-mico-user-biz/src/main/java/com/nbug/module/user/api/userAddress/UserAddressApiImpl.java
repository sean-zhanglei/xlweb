package com.nbug.module.user.api.userAddress;

import com.nbug.mico.common.model.user.UserAddress;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.service.UserAddressService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class UserAddressApiImpl implements UserAddressApi {

    @Resource
    private UserAddressService userAddressService;


    /**
     * 获取默认地址
     * @return UserAddress
     */
    @Override
    public CommonResult<UserAddress> getDefaultByUid(Integer userId) {
        return success(userAddressService.getDefaultByUid(userId));
    }

    @Override
    public CommonResult<UserAddress> getById(Integer addressId) {
        return success(userAddressService.getById(addressId));
    }

}
