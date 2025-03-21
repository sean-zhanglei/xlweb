package com.nbug.module.user.controller.admin;

import com.nbug.mico.common.model.finance.UserExtract;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.UserExtractRequest;
import com.nbug.mico.common.request.UserExtractSearchRequest;
import com.nbug.mico.common.response.BalanceResponse;
import com.nbug.module.user.service.UserExtractService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户提现表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("user/finance/apply")
@Tag(name = "管理后台 - 财务 -- 提现申请")
public class UserExtractController {

    @Autowired
    private UserExtractService userExtractService;

    /**
     * 分页显示用户提现表
     * @param request 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:finance:apply:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserExtract>>  getList(@Validated UserExtractSearchRequest request, @Validated PageParamRequest pageParamRequest){
        CommonPage<UserExtract> userExtractCommonPage = CommonPage.restPage(userExtractService.getList(request, pageParamRequest));
        return CommonResult.success(userExtractCommonPage);
    }

    /**
     * 修改用户提现表
     * @param id integer id
     * @param userExtractRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:finance:apply:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> update(@RequestParam Integer id, @Validated UserExtractRequest userExtractRequest){
        userExtractService.updateExtract(id, userExtractRequest);
        return CommonResult.success("success");
    }

    /**
     * 提现统计
     * @Param dateLimit 时间限制 today,yesterday,lately7,lately30,month,year,/yyyy-MM-dd hh:mm:ss,yyyy-MM-dd hh:mm:ss/
     */
    @PreAuthorize("hasAuthority('admin:finance:apply:balance')")
    @Operation(summary = "提现统计")
    @RequestMapping(value = "/balance", method = RequestMethod.POST)
    public CommonResult<BalanceResponse> balance(@RequestParam(value = "dateLimit", required = false,defaultValue = "")
                    String dateLimit){
        return CommonResult.success(userExtractService.getBalance(dateLimit));
    }

    /**
     * 提现审核
     * @param id    提现id
     * @param status    审核状态 -1 未通过 0 审核中 1 已提现
     * @param backMessage   驳回原因
     * @return 审核结果
     */
    @PreAuthorize("hasAuthority('admin:finance:apply:apply')")
    @Operation(summary = "提现申请审核")
    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public CommonResult<String> updateStatus(@RequestParam(value = "id") Integer id,
                                             @RequestParam(value = "status",defaultValue = "审核状态 -1 未通过 0 审核中 1 已提现") Integer status,
                                             @RequestParam(value = "backMessage",defaultValue = "驳回原因", required = false) String backMessage){
        userExtractService.updateStatus(id, status, backMessage);
        return CommonResult.success("success");
    }
}



