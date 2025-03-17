package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.wechat.WechatMediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


/**
 * 微信回复表 前端控制器
 */
@Slf4j
@RestController
@RequestMapping("infra/wechat/media")
@Tag(name = "管理后台 - 微信开放平台 -- 素材")
public class WechatMediaController {

    @Autowired
    private WechatMediaService wechatMediaService;

    /**
     * 上传
     */
    @PreAuthorize("hasAuthority('admin:wechat:media:upload')")
    @Operation(summary = "上传")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @Parameters(value = {
            @Parameter(name = "media", description = "待上传素材图片文件", required = true),
            @Parameter(name = "type", description = "媒体文件类型，分别有图片（image）、语音（voice", required = true)
    })
    public CommonResult<Map<String, String>> upload(
            @RequestParam("media") MultipartFile file,
            @RequestParam("type")  String type
    ) {
        return CommonResult.success(wechatMediaService.upload(file, type));
    }
}



