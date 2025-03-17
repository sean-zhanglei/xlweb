package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.SystemConfigAdminRequest;
import com.nbug.mico.common.request.SystemFormCheckRequest;
import com.nbug.module.system.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 配置表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("system/config")
@Tag(name = "管理后台 - 设置 -- Config")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    /**
     * 查询配置表信息
     * @param formId Integer
     */
    @PreAuthorize("hasAuthority('admin:system:config:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<HashMap<String, String>> info(@RequestParam(value = "formId") Integer formId) {
        return CommonResult.success(systemConfigService.info(formId));
    }


    /**
     * 整体保存表单数据
     * @param systemFormCheckRequest SystemFormCheckRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:system:config:save:form')")
    @Operation(summary = "整体保存表单数据")
    @RequestMapping(value = "/save/form", method = RequestMethod.POST)
    public CommonResult<String> saveFrom(@RequestBody @Validated SystemFormCheckRequest systemFormCheckRequest) {
        if (systemConfigService.saveForm(systemFormCheckRequest)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }

    /**
     * 检测表单name是否存在
     * @param name name
     */
    @PreAuthorize("hasAuthority('admin:system:config:check')")
    @Operation(summary = "检测表单name是否存在")
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public CommonResult<Boolean> check(@RequestParam String name) {
        return CommonResult.success(systemConfigService.checkName(name));
    }

    /**
     * 配置表中仅仅存储对应的配置
     * @param key 配置表中的配置字段
     * @param value 对应的值
     */
    @PreAuthorize("hasAuthority('admin:system:config:saveuniq')")
    @Operation(summary = "表单配置中仅仅存储")
    @RequestMapping(value = "/saveuniq", method = RequestMethod.POST)
    public CommonResult<Boolean> justSaveUniq(@RequestParam String key, @RequestParam String value) {
        return CommonResult.success(systemConfigService.updateOrSaveValueByName(key, value));
    }

    /**
     * 根据key获取表单配置数据
     * @param key 配置表的的字段
     */
    @PreAuthorize("hasAuthority('admin:system:config:getuniq')")
    @Operation(summary = "表单配置根据key获取")
    @RequestMapping(value = "/getuniq", method = RequestMethod.GET)
    public CommonResult<Object> justGetUniq(@RequestParam String key) {
        return CommonResult.success(systemConfigService.getValueByKey(key));
    }

    /**
     * 根据key获取配置
     */
    @PreAuthorize("hasAuthority('admin:system:config:get')")
    @Operation(summary = "根据key获取配置")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public CommonResult<List<SystemConfig>> getByKey(@RequestParam String key) {
        return CommonResult.success(systemConfigService.getListByKey(key));
    }

    /**
     * 更新配置信息
     */
    @PreAuthorize("hasAuthority('admin:system:config:update')")
    @Operation(summary = "更新配置信息")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public CommonResult<String> getByKey(@RequestBody @Validated List<SystemConfigAdminRequest> requestList) {
        if (systemConfigService.updateByList(requestList)) {
            return CommonResult.success("success");
        }
        return CommonResult.error(INTERNAL_SERVER_ERROR);
    }
}



