package com.nbug.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.nbug.common.response.CommonResult;
import com.nbug.common.constants.Constants;
import com.nbug.common.exception.XlwebException;
import com.nbug.front.service.QrCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 验证码
 
 */

@Slf4j
@RestController
@RequestMapping("api/front/qrcode")
@Api(tags = "二维码服务")
public class QrCodeController {

    @Autowired
    private QrCodeService qrCodeService;
    /**
     * 获取二维码
     * @return CommonResult
     */
    @ApiOperation(value="获取二维码")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> get(@RequestBody JSONObject data) {
        return CommonResult.success(qrCodeService.get(data));
    }

    /**
     * 远程图片转base64
     * @return CommonResult
     */
    @ApiOperation(value="远程图片转base64")
    @RequestMapping(value = "/base64", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> get(@RequestParam String url) {
        return CommonResult.success(qrCodeService.base64(url));
    }

    /**
     * 将字符串 转base64
     * @return CommonResult
     */
    @ApiOperation(value="将字符串 转base64")
    @RequestMapping(value = "/str2base64", method = RequestMethod.POST)
    public CommonResult<Map<String, Object>> getQrcodeByString(
            @RequestParam String text,
            @RequestParam int width,
            @RequestParam int height) {
        if((width < 50 || height < 50) && (width > 500 || height > 500) && text.length() >= 999){
            throw new XlwebException(Constants.RESULT_QRCODE_PRAMERROR);
        }
        return CommonResult.success(qrCodeService.base64String(text, width,height));
    }
}



