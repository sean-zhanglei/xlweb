package com.nbug.module.store.api.storeOrderInfo;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.api.user.UserApi;
import com.nbug.module.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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

    /**
     * 更新用户积分
     * @param user 用户
     * @param integral 积分
     * @param type 增加add、扣减sub
     * @return 更新后的用户对象
     */
    @Override
    public CommonResult<Boolean> updateIntegral(User user, Integer integral, String type) {
        return success(userService.updateIntegral(user, integral, type));
    }

    /**
     * 获取用户ById
     * @param id
     * @return
     */
    @Override
    public CommonResult<User> getById(Integer id) {
        return success(userService.getById(id));
    }

    /**
     * 添加/扣减积分
     * @param uid
     * @param price
     * @param nowMoney
     * @param type
     * @return
     */
    @Override
    public CommonResult<Boolean> operationNowMoney(Integer uid, BigDecimal price, BigDecimal nowMoney, String type) {
        return success(userService.operationNowMoney(uid, price, nowMoney, type));
    }

    /**
     * 获取个人资料
     *
     * @return User
     * @author Mr.Zhang
     * @since 2020-04-28
     */
    @Override
    public CommonResult<User> getInfo() {
        return success(userService.getInfo());
    }

    /**
     * 获取个人资料
     *
     * @return User
     * @author Mr.Zhang
     * @since 2020-04-28
     */
    @Override
    public CommonResult<User> getInfoException() {
        return success(userService.getInfoException());
    }

    /**
     * 获取当前用户id
     *
     * @return Integer
     */
    @Override
    public CommonResult<Integer> getUserIdException() {
        return success(userService.getUserIdException());
    }

    /**
     * 获取当前用户id
     *
     * @return Integer
     * @author Mr.Zhang
     * @since 2020-04-28
     */
    @Override
    public CommonResult<Integer> getUserId() {
        return success(userService.getUserId());
    }

    @Override
    public CommonResult<Boolean> updateById(User user) {
        return success(userService.updateById(user));
    }

    /**
     * 根据用户id获取用户列表 map模式
     * @param uidList uidList
     * @return HashMap
     */
    @Override
    public CommonResult<HashMap<Integer, User>> getMapListInUid(List<Integer> uidList) {
        return success(userService.getMapListInUid(uidList));
    }

}
