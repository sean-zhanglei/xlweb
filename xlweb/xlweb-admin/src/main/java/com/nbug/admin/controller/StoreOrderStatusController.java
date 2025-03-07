package com.nbug.admin.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.order.StoreOrderStatus;
import com.nbug.common.request.StoreOrderStatusSearchRequest;
import com.nbug.service.service.StoreOrderStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单操作记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/store/order/status")
@Api(tags = "订单 -- 操作记录") //配合swagger使用

public class StoreOrderStatusController {

    @Autowired
    private StoreOrderStatusService storeOrderStatusService;

    /**
     * 分页显示订单操作记录表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:order:status:list')")
    @ApiOperation(value = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreOrderStatus>>  getList(@Validated StoreOrderStatusSearchRequest request,
                                                               @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreOrderStatus> storeOrderStatusCommonPage = CommonPage.restPage(storeOrderStatusService.getList(request, pageParamRequest));
        return CommonResult.success(storeOrderStatusCommonPage);
    }
}



