package com.nbug.module.infra.controller.app;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.vo.FileResultVo;
import com.nbug.module.infra.service.attachment.UploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 商品表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("infra/user/upload")
@Tag(name = "应用后台 - 上传文件")
public class UploadFrontController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     */
    @Operation(summary = "图片上传")
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    @Parameters({
            @Parameter(name = "model", description = "模块 用户user,商品product,微信wechat,news文章"),
            @Parameter(name = "pid", description = "分类ID 0编辑器,1商品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图,7前台用户,8微信系列 ")
    })
    public CommonResult<FileResultVo> image(MultipartFile multipart, @RequestParam(value = "model") String model,
                                            @RequestParam(value = "pid") Integer pid) throws IOException {
        return CommonResult.success(uploadService.imageUpload(multipart, model, pid));
    }
}



