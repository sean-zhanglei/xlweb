package com.nbug.module.infra.controller.admin;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.utils.ImageMergeUtil;
import com.nbug.mico.common.vo.ImageMergeUtilVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片操作

 */
@Slf4j
@RestController
@RequestMapping("infra/qrcode")
@Tag(name = "管理后台 - 图片操作")
public class ImageMergeController {

    @PreAuthorize("hasAuthority('public:qrcode:merge:list')")
    @Operation(summary = "合并图片返回文件")
    @RequestMapping(value = "/mergeList", method = RequestMethod.POST)
    public CommonResult<Map<String, String>> mergeList(@RequestBody @Validated List<ImageMergeUtilVo> list){
        Map<String, String> map = new HashMap<>();
        map.put("base64Code", ImageMergeUtil.drawWordFile(list)); //需要云服务域名，如果需要存入数据库参照上传图片服务
        return CommonResult.success(map);
    }
}
