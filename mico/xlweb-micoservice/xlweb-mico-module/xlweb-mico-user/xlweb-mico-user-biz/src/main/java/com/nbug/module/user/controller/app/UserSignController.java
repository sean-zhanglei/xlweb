package com.nbug.module.user.controller.app;

import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.UserSignInfoResponse;
import com.nbug.mico.common.vo.SystemGroupDataSignConfigVo;
import com.nbug.mico.common.vo.UserSignMonthVo;
import com.nbug.mico.common.vo.UserSignVo;
import com.nbug.module.user.service.UserSignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


/**
 * 签到记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("/user/sign")
@Tag(name = "应用后台 - 用户 -- 签到")
public class UserSignController {

    @Autowired
    private UserSignService userSignService;

    /**
     * 签到列表
     * @param pageParamRequest 分页参数
     */
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserSignVo>>  getList(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserSignVo> userSignCommonPage = CommonPage.restPage(userSignService.getList(pageParamRequest));
        return CommonResult.success(userSignCommonPage);
    }

    /**
     * 签到列表，年月纬度
     * @param pageParamRequest 分页参数
     */
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/month", method = RequestMethod.GET)
    public CommonResult<CommonPage<UserSignMonthVo>>  getListGroupMonth(@Validated PageParamRequest pageParamRequest) {
        CommonPage<UserSignMonthVo> userSignCommonPage = CommonPage.restPage(userSignService.getListGroupMonth(pageParamRequest));
        return CommonResult.success(userSignCommonPage);
    }

    /**
     * 配置
     */
    @Operation(summary = "配置")
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public CommonResult<List<SystemGroupDataSignConfigVo>> config() {
        return CommonResult.success(userSignService.getSignConfig());
    }

    /**
     * 签到
     */
    @Operation(summary = "签到")
    @RequestMapping(value = "/integral", method = RequestMethod.GET)
    public CommonResult<SystemGroupDataSignConfigVo> info() {
        return CommonResult.success(userSignService.sign());
    }

    /**
     * 今日记录详情
     */
    @Operation(summary = "今日记录详情")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonResult<HashMap<String, Object>> get() {
        return CommonResult.success(userSignService.get());
    }

    /**
     * 签到用户信息
     */
    @Operation(summary = "签到用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public CommonResult<UserSignInfoResponse> getUserInfo() {
        return CommonResult.success(userSignService.getUserSignInfo());
    }
}



