package com.nbug.module.store.controller.admin;

import com.nbug.mico.common.model.order.StoreOrderStatus;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreOrderStatusSearchRequest;
import com.nbug.module.store.service.StoreOrderStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("store/order/status")
@Tag(name = "管理后台 - 订单 -- 操作记录") //配合swagger使用

public class StoreOrderStatusController {

    @Autowired
    private StoreOrderStatusService storeOrderStatusService;

    /**
     * 分页显示订单操作记录表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:order:status:list')")
    @Operation(summary = "分页列表") //配合swagger使用
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<StoreOrderStatus>>  getList(@Validated StoreOrderStatusSearchRequest request,
                                                               @Validated PageParamRequest pageParamRequest){
        CommonPage<StoreOrderStatus> storeOrderStatusCommonPage = CommonPage.restPage(storeOrderStatusService.getList(request, pageParamRequest));
        return CommonResult.success(storeOrderStatusCommonPage);
    }
}



