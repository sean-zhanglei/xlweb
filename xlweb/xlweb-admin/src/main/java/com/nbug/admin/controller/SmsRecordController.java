package com.nbug.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.nbug.common.response.CommonResult;
import com.nbug.common.vo.MyRecord;
import com.nbug.common.request.PageParamRequest;
import com.nbug.service.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 短信发送记录表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("api/admin/sms")
@Api(tags = "短信服务")
public class SmsRecordController {

    @Autowired
    private SmsService smsService;

//    /**
//     * 短信模板
//     */
//    @PreAuthorize("hasAuthority('admin:sms:temps')")
//    @ApiOperation(value = "短信模板")
//    @RequestMapping(value = "/temps", method = RequestMethod.GET)
//    public CommonResult<Map<String, Object>> temps(@ModelAttribute PageParamRequest pageParamRequest) {
//        MyRecord myRecord = smsService.temps(pageParamRequest);
//        return CommonResult.success(myRecord);
//    }

}


