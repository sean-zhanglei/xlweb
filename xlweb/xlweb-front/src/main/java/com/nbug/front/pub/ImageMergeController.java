package com.nbug.front.pub;

import com.nbug.common.response.CommonResult;
import com.nbug.common.utils.ImageMergeUtil;
import com.nbug.common.vo.ImageMergeUtilVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("api/public/qrcode")
@Api(tags = "图片操作")
public class ImageMergeController {

    @ApiOperation(value = "合并图片返回文件")
    @RequestMapping(value = "/mergeList", method = RequestMethod.POST)
    public CommonResult<Map<String, String>> mergeList(@RequestBody @Validated List<ImageMergeUtilVo> list){
        Map<String, String> map = new HashMap<>();
        map.put("base64Code", ImageMergeUtil.drawWordFile(list)); //需要云服务域名，如果需要存入数据库参照上传图片服务
        return CommonResult.success(map);
    }
}
