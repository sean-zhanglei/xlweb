package com.nbug.module.system.controller.admin;


import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.RetailShopRequest;
import com.nbug.mico.common.request.RetailShopStairUserRequest;
import com.nbug.mico.common.response.SpreadOrderResponse;
import com.nbug.mico.common.response.SpreadUserResponse;
import com.nbug.module.system.service.RetailShopService;
import com.nbug.module.user.api.user.UserApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 分销模块

 */
@Slf4j
@RestController
@RequestMapping("system/store/retail")
@Tag(name = "管理后台 - 分销")
public class RetailShopController {

    @Autowired
    private RetailShopService retailShopService;

    @Autowired
    private UserApi userApi;

    /**
     * 分销员列表
     * @param keywords         搜索参数
     * @param dateLimit        时间参数
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:retail:list')")
    @Operation(summary = "分销员列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameters({
            @Parameter(name = "keywords", description = "搜索关键字[姓名、电话、uid]"),
            @Parameter(name = "dateLimit", description = "today,yesterday,lately7,lately30,month,year,/yyyy-MM-dd hh:mm:ss,yyyy-MM-dd hh:mm:ss/")
    })
    public CommonResult<CommonPage<SpreadUserResponse>> getList(@RequestParam(required = false) String keywords,
                                                                @RequestParam(required = false) String dateLimit,
                                                                @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(retailShopService.getSpreadPeopleList(keywords, dateLimit, pageParamRequest));
    }

    /**
     * 根据用户参数获取推广人列表
     * @param request          查询参数
     * @param pageParamRequest 分页参数
     * @return 查询结果推广人列表
     */
    @PreAuthorize("hasAuthority('admin:retail:spread:list')")
    @Operation(summary = "根据条件获取推广人列表")
    @RequestMapping(value = "/spread/userlist", method = RequestMethod.POST)
    public CommonResult<CommonPage<User>> getUserListBySpreadLevel(@RequestBody @Validated RetailShopStairUserRequest request,
                                                                   @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userApi.getUserListBySpreadLevel(request, pageParamRequest).getCheckedData()));
    }


    /**
     * 根据参数获取推广订单列表
     * @param request          查询参数
     * @param pageParamRequest 分页参数
     * @return 查询结果推广人订单列表
     */
    @PreAuthorize("hasAuthority('admin:retail:spread:order:list')")
    @Operation(summary = "根据条件获取推广人订单")
    @RequestMapping(value = "/spread/orderlist", method = RequestMethod.POST)
    public CommonResult<CommonPage<SpreadOrderResponse>> getOrdersBySpreadLevel(@RequestBody @Validated RetailShopStairUserRequest request,
                                                                                @ModelAttribute PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(userApi.getOrderListBySpreadLevel(request, pageParamRequest).getCheckedData()));
    }

    /**
     * 清除上级推广人
     * @param id 当前被清理的用户id
     * @return 推广关系清理后的结果
     */
    @PreAuthorize("hasAuthority('admin:retail:spread:clean')")
    @Operation(summary = "清除上级推广人")
    @RequestMapping(value = "/spread/clean/{id}", method = RequestMethod.GET)
    public CommonResult<Object> clearSpread(@PathVariable Integer id) {
        return CommonResult.success(userApi.clearSpread(id).getCheckedData());
    }

    /**
     * 分销设置获取
     * @return 保存分销设置
     */
    @PreAuthorize("hasAuthority('admin:retail:spread:manage:get')")
    @Operation(summary = "分销配置信息获取")
    @RequestMapping(value = "/spread/manage/get", method = RequestMethod.GET)
    public CommonResult<Object> getSpreadInfo() {
        return CommonResult.success(retailShopService.getManageInfo());
    }

    /**
     * 分销管理信息保存
     * @param retailShopRequest 分销管理对象
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('admin:retail:spread:manage:set')")
    @Operation(summary = "分销管理信息保存")
    @RequestMapping(value = "/spread/manage/set", method = RequestMethod.POST)
    public CommonResult<Object> setSpreadInfo(@RequestBody @Validated RetailShopRequest retailShopRequest) {
        return CommonResult.success(retailShopService.setManageInfo(retailShopRequest));
    }
}
