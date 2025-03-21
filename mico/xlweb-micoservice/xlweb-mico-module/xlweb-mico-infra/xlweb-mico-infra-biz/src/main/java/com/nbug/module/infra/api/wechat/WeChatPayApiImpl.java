package com.nbug.module.infra.api.wechat;

import com.nbug.mico.common.model.finance.UserRecharge;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.infra.service.wechat.WeChatPayService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class WeChatPayApiImpl implements WeChatPayApi{

    @Resource
    private WeChatPayService weChatPayService;

    /**
     * 微信充值预下单接口
     * @param userRecharge 充值订单
     * @param clientIp      ip
     * @return 获取wechat.requestPayment()参数
     */
    @Override
    public CommonResult<Map<String, String>> unifiedRecharge( UserRecharge userRecharge, String clientIp) {
        Map<String, String> map = weChatPayService.unifiedRecharge(userRecharge, clientIp);
        return CommonResult.success(map);
    }

    /**
     * 查询支付结果
     * @param orderNo 订单编号
     * @return
     */
    @Override
    public CommonResult<Boolean> queryPayResult(String orderNo) {
        Boolean result = weChatPayService.queryPayResult(orderNo);
        return CommonResult.success(result);
    }
}
