package com.nbug.module.user.controller.admin;


import com.nbug.mico.common.model.user.UserBrokerageRecord;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.BrokerageRecordRequest;
import com.nbug.mico.common.request.FundsMonitorRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.MonitorResponse;
import com.nbug.module.user.service.UserBillService;
import com.nbug.module.user.service.UserFundsMonitorService;
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
 * 用户提现表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/user/finance/founds/monitor")
@Tag(name = "财务 -- 资金监控")
public class UserFundsMonitorController {

    @Autowired
    private UserBillService userBillService;

    @Autowired
    private UserFundsMonitorService userFundsMonitorService;

    /**
     * 分页显示资金监控
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:finance:monitor:list')")
    @Operation(summary = "资金监控")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<MonitorResponse>>  getList(@Validated FundsMonitorRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<MonitorResponse> userExtractCommonPage = CommonPage.restPage(userBillService.fundMonitoring(request, pageParamRequest));
        return CommonResult.success(userExtractCommonPage);
    }

    /**
     * 佣金记录
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:finance:monitor:brokerage:record')")
    @Operation(summary = "佣金记录")
    @RequestMapping(value = "/brokerage/record", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserBrokerageRecord>> brokerageRecord(@Validated BrokerageRecordRequest request, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(CommonPage.restPage(userFundsMonitorService.getBrokerageRecord(request, pageParamRequest)));
    }
}



