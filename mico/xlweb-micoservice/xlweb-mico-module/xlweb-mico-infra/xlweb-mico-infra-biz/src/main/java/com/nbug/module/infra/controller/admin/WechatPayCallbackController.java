package com.nbug.module.infra.controller.admin;

import com.nbug.module.infra.service.wechat.CallbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 支付回调

 */
@Slf4j
@RestController
@RequestMapping("api/infra/payment/callback")
@Tag(name = "支付回调")
public class WechatPayCallbackController {

    @Autowired
    private CallbackService callbackService;

    /**
     * 微信支付回调
     */
    @Operation(summary = "微信支付回调")
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public String weChat(@RequestBody String  request) {
        System.out.println("微信支付回调 request ===> " + request);
        String response = callbackService.weChat(request);
        System.out.println("微信支付回调 response ===> " + response);
        return response;
    }

    /**
     * 微信退款回调
     */
    @Operation(summary = "微信退款回调")
    @RequestMapping(value = "/wechat/refund", method = RequestMethod.POST)
    public String weChatRefund(@RequestBody String request) {
        System.out.println("微信退款回调 request ===> " + request);
        String response = callbackService.weChatRefund(request);
        System.out.println("微信退款回调 response ===> " + response);
        return response;
    }
}



