package com.nbug.module.infra.api.attachment;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.attachment.SystemAttachmentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class AttachmentApiImpl implements AttachmentApi {

    @Resource
    private SystemAttachmentService systemAttachmentService;

    /**
     * 清除 cdn url， 在保存数据的时候使用
     * @param path
     * @return
     */
    @Override
    public CommonResult<String> clearPrefix(String path) {
        return CommonResult.success(systemAttachmentService.clearPrefix(path));
    }

    /**
     * 给图片加前缀
     * @param path String 路径
     * @return String
     */
    @Override
    public CommonResult<String> prefixImage(String path) {
        return CommonResult.success(systemAttachmentService.prefixImage(path));
    }

}
