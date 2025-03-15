package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.constants.Constants;
import com.nbug.module.system.api.config.ConfigApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: xlweb
 * @author: 大粽子
 * @create: 2021-09-23 09:18
 **/
@Slf4j
@RestController
@RequestMapping("api/admin/infra/jsconfig")
@Tag(name = "公共JS配置")
public class GetJSConfigController {

    @Autowired
    private ConfigApi configApi;

    @PreAuthorize("hasAuthority('public:jsconfig:getxlwebchatconfig')")
    @Operation(summary = "XLWEB-chat客服统计")
    @RequestMapping(value = "/getxlwebchatconfig", method = RequestMethod.GET)
    public String set(){
        return configApi.getValueByKey(Constants.JS_CONFIG_XLWEB_CHAT_TONGJI).getCheckedData();
    }
}
