package com.nbug.module.infra.controller.admin;

import com.nbug.module.infra.service.sms.SmsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 短信发送记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/infra/sms")
@Tag(name = "短信服务")
@Validated
public class SmsRecordController {

    @Autowired
    private SmsService smsService;

//    /**
//     * 短信模板
//     */
//    @PreAuthorize("hasAuthority('admin:sms:temps')")
//    @Operation(summary = "短信模板")
//    @RequestMapping(value = "/temps", method = RequestMethod.GET)
//    public CommonResult<Map<String, Object>> temps(@ModelAttribute PageParamRequest pageParamRequest) {
//        MyRecord myRecord = smsService.temps(pageParamRequest);
//        return CommonResult.success(myRecord);
//    }

}


