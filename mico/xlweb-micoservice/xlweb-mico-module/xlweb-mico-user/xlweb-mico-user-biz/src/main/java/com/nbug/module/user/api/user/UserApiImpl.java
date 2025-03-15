package com.nbug.module.user.api.user;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.RetailShopStairUserRequest;
import com.nbug.mico.common.response.SpreadOrderResponse;
import com.nbug.module.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 更新余额
     * @param user 用户
     * @param price 金额
     * @param type 增加add、扣减sub
     * @return 更新后的用户数据
     */
    @Override
    public CommonResult<Boolean> updateNowMoney(User user, BigDecimal price, String type) {
        return success(userService.updateNowMoney(user, price, type));
    }


    /**
     * 清除User Group id
     * @param groupId 待清除的GroupId
     */
    @Override
    public CommonResult<Boolean> clearGroupByGroupId(String groupId) {
        try {
            userService.clearGroupByGroupId(groupId);
            return success(true);
        } catch (Exception e) {
            return CommonResult.error(GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 清除对应的用户等级
     * @param levelId 等级id
     */
    @Override
    public CommonResult<Boolean> removeLevelByLevelId(Integer levelId) {
        return success(userService.removeLevelByLevelId(levelId));
    }

    /**
     * PC后台分销员列表
     * @param keywords 搜索参数
     * @param dateLimit 时间参数
     * @param pageRequest 分页参数
     * @return
     */
    @Override
    public CommonResult<PageInfo<User>> getAdminSpreadPeopleList(String keywords, String dateLimit, PageParamRequest pageRequest) {
        return success(userService.getAdminSpreadPeopleList(keywords, dateLimit, pageRequest));
    }

    /**
     * 推广人排行
     * @param minPayCount
     * @param maxPayCount
     * @return
     */
    @Override
    public CommonResult<Integer> getCountByPayCount(int minPayCount, int maxPayCount) {
        return success(userService.getCountByPayCount(minPayCount, maxPayCount));
    }

    /**
     * 按开始结束时间查询每日新增用户数量
     * @param date
     * @return
     */
    @Override
    public CommonResult<Map<Object, Object>> getAddUserCountGroupDate(String date) {
        return success(userService.getAddUserCountGroupDate(date));
    }

    /**
     * 根据日期获取注册用户数量
     * @param date
     * @return
     */
    @Override
    public CommonResult<Integer> getRegisterNumByDate(String date) {
        return success(userService.getRegisterNumByDate(date));
    }

    /**
     * 根据推广级别和其他参数当前用户下的推广列表
     *
     * @param request 推广列表参数
     * @return 当前用户的推广人列表
     */
    @Override
    public CommonResult<PageInfo<User>> getUserListBySpreadLevel(RetailShopStairUserRequest request, PageParamRequest pageParamRequest) {
        return success(userService.getUserListBySpreadLevel(request, pageParamRequest));
    }

    /**
     * 根据推广级别和其他参数获取推广列表
     *
     * @param request 推广层级和推广时间参数
     * @return 推广订单列表
     */
    @Override
    public CommonResult<PageInfo<SpreadOrderResponse>> getOrderListBySpreadLevel(RetailShopStairUserRequest request, PageParamRequest pageParamRequest) {
        return success(userService.getOrderListBySpreadLevel(request, pageParamRequest));
    }

    /**
     * 根据用户id清除用户当前推广人
     *
     * @param userId 当前推广人id
     * @return 清除推广结果
     */
    @Override
    public CommonResult<Boolean> clearSpread(Integer userId) {
        return success(userService.clearSpread(userId));
    }
}
