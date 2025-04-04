package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.response.HomeRateResponse;
import com.nbug.module.system.service.HomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * 统计 -- 主页 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/statistics/home")
@Tag(name = "管理后台 - 统计 -- 主页")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @PreAuthorize("hasAuthority('admin:statistics:home:index')")
    @Operation(summary = "首页数据")
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public CommonResult<HomeRateResponse> indexDate() {
        return CommonResult.success(homeService.indexDate());
    }

    /**
     * 用户曲线图
     */
    @PreAuthorize("hasAuthority('admin:statistics:home:chart:user')")
    @Operation(summary = "用户曲线图")
    @RequestMapping(value = "/chart/user", method = RequestMethod.GET)
    public CommonResult<Map<Object, Object>> chartUser() {
        return CommonResult.success(homeService.chartUser());
    }

    /**
     * 用户购买统计
     */
    @PreAuthorize("hasAuthority('admin:statistics:home:chart:user:buy')")
    @Operation(summary = "用户购买统计")
    @RequestMapping(value = "/chart/user/buy", method = RequestMethod.GET)
    public CommonResult<Map<String, Integer>> chartUserBuy() {
        return CommonResult.success(homeService.chartUserBuy());
    }

    /**
     * 30天订单量趋势
     */
    @PreAuthorize("hasAuthority('admin:statistics:home:chart:order')")
    @Operation(summary = "30天订单量趋势")
    @RequestMapping(value = "/chart/order", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> chartOrder() {
        return CommonResult.success(homeService.chartOrder());
    }

    /**
     * 周订单量趋势
     */
    @PreAuthorize("hasAuthority('admin:statistics:home:chart:order:week')")
    @Operation(summary = "周订单量趋势")
    @RequestMapping(value = "/chart/order/week", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> chartOrderInWeek() {
        return CommonResult.success(homeService.chartOrderInWeek());
    }

    /**
     * 月订单量趋势
     */
    @PreAuthorize("hasAuthority('admin:statistics:home:chart:order:month')")
    @Operation(summary = "月订单量趋势")
    @RequestMapping(value = "/chart/order/month", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> chartOrderInMonth() {
        return CommonResult.success(homeService.chartOrderInMonth());
    }

    /**
     * 年订单量趋势
     */
    @PreAuthorize("hasAuthority('admin:statistics:home:chart:order:year')")
    @Operation(summary = "年订单量趋势")
    @RequestMapping(value = "/chart/order/year", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> chartOrderInYear() {
        return CommonResult.success(homeService.chartOrderInYear());
    }
}



