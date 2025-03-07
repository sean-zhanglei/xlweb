package com.nbug.admin.controller;

import com.nbug.common.request.ShippingTemplatesFreeRequest;
import com.nbug.common.response.CommonResult;
import com.nbug.service.service.ShippingTemplatesFreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  物流控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/express/shipping/free")
@Api(tags = "设置 -- 物流 -- 免费")
public class ShippingTemplatesFreeController {

    @Autowired
    private ShippingTemplatesFreeService shippingTemplatesFreeService;

    /**
     * 根据模板id查询数据
     * @param tempId Integer 模板id
     */
    @PreAuthorize("hasAuthority('admin:shipping:templates:free:list')")
    @ApiOperation(value = "根据模板id查询数据")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<List<ShippingTemplatesFreeRequest>> getList(@RequestParam Integer tempId){
        return CommonResult.success(shippingTemplatesFreeService.getListGroup(tempId));
    }

}
