package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.ShippingTemplatesRegionRequest;
import com.nbug.module.system.service.ShippingTemplatesRegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  物流付费前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("system/express/shipping/region")
@Tag(name = "管理后台 - 设置 -- 物流 -- 付费")
public class ShippingTemplatesRegionController {

    @Autowired
    private ShippingTemplatesRegionService shippingTemplatesRegionService;

    /**
     * 根据模板id查询数据
     * @param tempId Integer 模板id
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:region:list')")
    @Operation(summary = "根据模板id查询数据")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<ShippingTemplatesRegionRequest>> getList(@RequestParam Integer tempId){
        return CommonResult.success(shippingTemplatesRegionService.getListGroup(tempId));
    }
}
