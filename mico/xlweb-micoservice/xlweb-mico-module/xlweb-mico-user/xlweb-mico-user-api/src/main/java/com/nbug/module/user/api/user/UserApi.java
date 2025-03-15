package com.nbug.module.user.api.user;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.RetailShopStairUserRequest;
import com.nbug.mico.common.response.SpreadOrderResponse;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户")
public interface UserApi {

    String PREFIX = ApiConstants.PREFIX + "/user";


    @GetMapping(PREFIX + "/getValidateCodeRedisKey")
    @Operation(summary = "检测手机验证码key")
    @Parameter(name = "phone", description = "检测手机验证码key", required = true)
    public CommonResult<String> getValidateCodeRedisKey(String phone);


    @PostMapping(PREFIX + "/updateIntegral")
    @Operation(summary = "更新用户积分")
    @Parameters({
            @Parameter(name = "user", description = "用户", required = true),
            @Parameter(name = "integral", description = "积分", required = true),
            @Parameter(name = "type", description = "类型", required = true)
    })

    public CommonResult<Boolean> updateIntegral(User user, Integer integral, String type);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取用户ById")
    @Parameter(name = "id", description = "id", required = true)
    public CommonResult<User> getById(Integer id);

    @PostMapping(PREFIX + "/operationNowMoney")
    @Operation(summary = "添加/扣减积分")
    @Parameters({
            @Parameter(name = "uid", description = "用户ID", required = true),
            @Parameter(name = "price", description = "金额", required = true),
            @Parameter(name = "nowMoney", description = "现金", required = true),
            @Parameter(name = "type", description = "类型", required = true)
    })
    public CommonResult<Boolean> operationNowMoney(Integer uid, BigDecimal price, BigDecimal nowMoney, String type);


    @GetMapping(PREFIX + "/getInfoException")
    @Operation(summary = "获取个人资料")
    public CommonResult<User> getInfoException();

    @GetMapping(PREFIX + "/getInfo")
    @Operation(summary = "获取个人资料")
    public CommonResult<User> getInfo();

    @GetMapping(PREFIX + "/getUserIdException")
    @Operation(summary = "获取当前用户id")
    public CommonResult<Integer> getUserIdException();

    @GetMapping(PREFIX + "/getUserId")
    @Operation(summary = "获取当前用户id")
    public CommonResult<Integer> getUserId();

    @PostMapping(PREFIX + "/updateById")
    @Operation(summary = "更新用户ById")
    @Parameter(name = "user", description = "用户", required = true)
    public CommonResult<Boolean> updateById(User user);

    @GetMapping(PREFIX + "/getMapListInUid")
    @Operation(summary = "根据用户id获取用户列表 map模式")
    @Parameter(name = "uidList", description = "uid List", required = true)
    public CommonResult<HashMap<Integer, User>> getMapListInUid(List<Integer> uidList);


    @PostMapping(PREFIX + "/updateNowMoney")
    @Operation(summary = "更新余额")
    @Parameters({
            @Parameter(name = "user", description = "用户", required = true),
            @Parameter(name = "price", description = "金额", required = true),
            @Parameter(name = "type", description = "类型", required = true)
    })
    public CommonResult<Boolean> updateNowMoney(User user, BigDecimal price, String type);


    @PostMapping(PREFIX + "/clearGroupByGroupId")
    @Operation(summary = "清空分组")
    @Parameter(name = "groupId", description = "分组id", required = true)
    public CommonResult<Boolean> clearGroupByGroupId(String groupId);

    @PostMapping(PREFIX + "/removeLevelByLevelId")
    @Operation(summary = "清除对应的用户等级")
    @Parameter(name = "levelId", description = "等级Id", required = true)
    public CommonResult<Boolean> removeLevelByLevelId(Integer levelId);


    @GetMapping(PREFIX + "/getAdminSpreadPeopleList")
    @Operation(summary = "获取后台推广人列表")
    @Parameters({
            @Parameter(name = "keywords", description = "搜索关键字"),
            @Parameter(name = "dateLimit", description = "时间范围"),
            @Parameter(name = "pageRequest", description = "分页参数", required = true)
    })
    public CommonResult<PageInfo<User>> getAdminSpreadPeopleList(String keywords, String dateLimit, PageParamRequest pageRequest);


    @GetMapping(PREFIX + "/getCountByPayCount")
    @Operation(summary = "推广人排行")
    @Parameters({
            @Parameter(name = "minPayCount", description = "最小支付金额", required = true),
            @Parameter(name = "maxPayCount", description = "最大支付金额", required = true)
    })
    public CommonResult<Integer> getCountByPayCount(int minPayCount, int maxPayCount);

    @GetMapping(PREFIX + "/getAddUserCountGroupDate")
    @Operation(summary = "按开始结束时间查询每日新增用户数量")
    @Parameter(name = "date", description = "日期", required = true)
    public CommonResult<Map<Object, Object>> getAddUserCountGroupDate(String date);

    @GetMapping(PREFIX + "/getRegisterNumByDate")
    @Operation(summary = "根据日期获取注册用户数量")
    @Parameter(name = "date", description = "日期", required = true)
    public CommonResult<Integer> getRegisterNumByDate(String date);

    @GetMapping(PREFIX + "/getUserListBySpreadLevel")
    @Operation(summary = "根据条件获取推广人列表")
    @Parameters({
            @Parameter(name = "request", description = "请求参数", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<PageInfo<User>> getUserListBySpreadLevel(RetailShopStairUserRequest request, PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getOrderListBySpreadLevel")
    @Operation(summary = "根据条件获取推广人订单")
    @Parameters({
            @Parameter(name = "request", description = "请求参数", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<PageInfo<SpreadOrderResponse>> getOrderListBySpreadLevel(RetailShopStairUserRequest request, PageParamRequest pageParamRequest);

    @PostMapping(PREFIX + "/clearSpread")
    @Operation(summary = "根据用户id清除用户当前推广人")
    @Parameter(name = "uid", description = "用户id", required = true)
    public CommonResult<Boolean> clearSpread(Integer uid);
}
