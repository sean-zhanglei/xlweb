package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.StoreBargainSearchRequest;
import com.nbug.mico.common.request.StoreCombinationSearchRequest;
import com.nbug.mico.common.request.StoreOrderSearchRequest;
import com.nbug.mico.common.request.StoreProductSearchRequest;
import com.nbug.module.system.service.ExcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


/**
 * Excel导出 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("system/export/excel")
@Tag(name = "管理后台 - 导出 -- Excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    /**
     * 商品导出
     * @param request 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:export:excel:product')")
    @Operation(summary = "产品")
    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public CommonResult<HashMap<String, String>> export(@Validated StoreProductSearchRequest request) {
        String fileName = excelService.exportProduct(request);
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        return CommonResult.success(map);
    }

    /**
     * 砍价商品导出
     * @param request 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:export:excel:bargain')")
    @Operation(summary = "砍价商品导出")
    @RequestMapping(value = "/bargain/product", method = RequestMethod.GET)
    public CommonResult<HashMap<String, String>> exportBargainProduct(@Validated StoreBargainSearchRequest request) {
        String fileName = excelService.exportBargainProduct(request);
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        return CommonResult.success(map);
    }

    /**
     * 拼团商品导出
     * @param request 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:export:excel:combiantion')")
    @Operation(summary = "拼团商品导出")
    @RequestMapping(value = "/combiantion/product", method = RequestMethod.GET)
    public CommonResult<HashMap<String, String>> exportCombinationProduct(@Validated StoreCombinationSearchRequest request) {
        String fileName = excelService.exportCombinationProduct(request);
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        return CommonResult.success(map);
    }

    /**
     * 订单导出
     * @param request 搜索条件
     */
    @PreAuthorize("hasAuthority('admin:export:excel:order')")
    @Operation(summary = "订单导出")
    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public CommonResult<HashMap<String, String>> exportOrder(@Validated StoreOrderSearchRequest request){
        String fileName = excelService.exportOrder(request);
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        return CommonResult.success(map);
    }

}



