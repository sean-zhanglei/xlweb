package com.nbug.module.infra.api.wechat;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.token.WeChatOauthToken;
import com.nbug.mico.common.vo.CreateOrderRequestVo;
import com.nbug.mico.common.vo.CreateOrderResponseVo;
import com.nbug.mico.common.vo.WeChatAuthorizeLoginUserInfoVo;
import com.nbug.mico.common.vo.WeChatMiniAuthorizeVo;
import com.nbug.mico.common.vo.WxRefundResponseVo;
import com.nbug.mico.common.vo.WxRefundVo;
import com.nbug.module.infra.service.wechat.WechatNewService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class WechatNewApiImpl implements WechatNewApi {

    @Resource
    private WechatNewService wechatNewService;

    /**
     * 微信预下单接口(统一下单)
     * @param unifiedorderVo 预下单请求对象
     * @return 微信预下单返回对象
     */
    @Override
    public CommonResult<CreateOrderResponseVo> payUnifiedorder(CreateOrderRequestVo unifiedorderVo) {
        CreateOrderResponseVo createOrderResponseVo = wechatNewService.payUnifiedorder(unifiedorderVo);
        return CommonResult.success(createOrderResponseVo);
    }


    /**
     * 微信申请退款
     * @param wxRefundVo 微信申请退款对象
     * @param path 商户p12证书绝对路径
     * @return 申请退款结果对象
     */
    @Override
    public CommonResult<WxRefundResponseVo> payRefund(WxRefundVo wxRefundVo, String path) {
        WxRefundResponseVo wxRefundResponseVo = wechatNewService.payRefund(wxRefundVo, path);
        return CommonResult.success(wxRefundResponseVo);
    }

    /**
     * 小程序登录凭证校验
     * @return
     */
    @Override
    public CommonResult<WeChatMiniAuthorizeVo> miniAuthCode(String code) {
        WeChatMiniAuthorizeVo weChatMiniAuthorizeVo = wechatNewService.miniAuthCode(code);
        return CommonResult.success(weChatMiniAuthorizeVo);
    }

    /**
     * 获取开放平台access_token 通过 code 获取 公众号使用
     * @param code
     * @return
     */
    @Override
    public CommonResult<WeChatOauthToken> getOauth2AccessToken(String code) {
        WeChatOauthToken weChatOauthToken = wechatNewService.getOauth2AccessToken(code);
        return CommonResult.success(weChatOauthToken);
    }

    /**
     * 获取开放平台用户信息
     * @param accessToken 调用凭证
     * @param openid 普通用户的标识，对当前开发者帐号唯一
     * 公众号使用
     * @return 开放平台用户信息对象
     */
    @Override
    public CommonResult<WeChatAuthorizeLoginUserInfoVo> getSnsUserInfo(String accessToken, String openid) {
        WeChatAuthorizeLoginUserInfoVo weChatAuthorizeLoginUserInfoVo = wechatNewService.getSnsUserInfo(accessToken, openid);
        return CommonResult.success(weChatAuthorizeLoginUserInfoVo);
    }
}
