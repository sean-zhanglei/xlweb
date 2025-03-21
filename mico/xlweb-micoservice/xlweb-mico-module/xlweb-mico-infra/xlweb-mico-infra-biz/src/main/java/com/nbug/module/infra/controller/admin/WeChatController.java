package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.wechat.WechatPublicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信缓存表 前端控制器

 */
@Slf4j
@RestController
@RequestMapping("infra/wechat/menu")
@Tag(name = "管理后台 - 微信开放平台 -- 菜单管理")
public class WeChatController {

    @Autowired
    private WechatPublicService wechatPublicService;

    /**
     * 获取微信菜单
     */
    @PreAuthorize("hasAuthority('admin:wechat:menu:public:get')")
    @Operation(summary = "获取自定义菜单")
    @RequestMapping(value = "/public/get", method = RequestMethod.GET)
    public CommonResult<Object> get() {
        return CommonResult.success(wechatPublicService.getCustomizeMenus());
    }

    /**
     * 创建微信菜单
     * @param data 菜单数据，具体json格式参考微信开放平台
     */
    @PreAuthorize("hasAuthority('admin:wechat:menu:public:create')")
    @Operation(summary = "保存自定义菜单")
    @RequestMapping(value = "/public/create", method = RequestMethod.POST)
    public CommonResult<String> create(@RequestBody String data) {
        wechatPublicService.createMenus(data);
        return CommonResult.success("success");
    }

    /**
     * 删除微信菜单
     */
    @PreAuthorize("hasAuthority('admin:wechat:menu:public:delete')")
    @Operation(summary = "删除自定义菜单")
    @RequestMapping(value = "/public/delete", method = RequestMethod.GET)
    public CommonResult<String> delete() {
        wechatPublicService.deleteMenus();
        return CommonResult.success("success");
    }
}



