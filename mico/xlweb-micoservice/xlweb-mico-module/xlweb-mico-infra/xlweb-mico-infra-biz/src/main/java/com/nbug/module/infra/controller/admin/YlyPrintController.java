package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.Yly.YlyPrintService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 易联云打印订单

 */
@Slf4j
@RestController
@RequestMapping("infra/yly")
@Tag(name = "管理后台 - 易联云 打印订单小票") //配合swagger使用
public class YlyPrintController {


    @Autowired
    private YlyPrintService ylyPrintService;

    @PreAuthorize("hasAuthority('admin:yly:print')")
    @Operation(summary = "打印小票")
    @RequestMapping(value = "/print/{ordid}", method = RequestMethod.GET)
    public CommonResult<String> updateStatus(@PathVariable  String ordid) {
        ylyPrintService.YlyPrint(ordid,false);
        return CommonResult.success("success");
    }
}
