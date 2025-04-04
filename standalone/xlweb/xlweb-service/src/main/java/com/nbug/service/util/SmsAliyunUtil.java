package com.nbug.service.util;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.nbug.common.constants.Constants;
import com.nbug.common.model.sms.SmsRecord;
import com.nbug.common.utils.DateUtil;
import com.nbug.common.vo.SendSmsVo;
import com.nbug.service.service.SmsRecordService;
import com.nbug.service.service.SystemConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SmsAliyunUtil {

    private static final Logger logger = LoggerFactory.getLogger(SmsAliyunUtil.class);


    private static SystemConfigService systemConfigService;

    private static SmsRecordService smsRecordService;

    @Autowired
    public void setSystemConfigService(SystemConfigService systemConfigService) {
        SmsAliyunUtil.systemConfigService = systemConfigService;
    }

    @Autowired
    public void setSmsRecordService(SmsRecordService smsRecordService) {
        SmsAliyunUtil.smsRecordService = smsRecordService;
    }

    private static volatile Client INSTANCE;

    private SmsAliyunUtil(){}

    public static Client getInstance() {
        if (INSTANCE == null){
            synchronized (SmsAliyunUtil.class) {
                if (INSTANCE == null) {
                    try {
                        //阿里云短信AccessKeyId
                        String accessKeyId = systemConfigService.getValueByKey(Constants.CONFIG_KEY_SMS_ACCESSKEY_ID);
                        //阿里云短息AccessKeySecret
                        String accessKeySecret = systemConfigService.getValueByKey(Constants.CONFIG_KEY_SMS_ACCESSKEY_SECRET);
                        //阿里云短信endPoint
                        String endPoint = systemConfigService.getValueByKey(Constants.CONFIG_KEY_SMS_ENDPOINT);
                        Config config = new Config()
                                .setAccessKeyId(accessKeyId)
                                .setAccessKeySecret(accessKeySecret);
                        config.endpoint = endPoint;

                        INSTANCE = new Client(config);
                    } catch (Exception ex) {
                        logger.error("阿里云短信Client实例化失败：" + ex.getLocalizedMessage(), ex);
                    }
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 阿里云短信发送
     * @param vo
     */
    public static SendSmsResponse sendSms(SendSmsVo vo) {
        Client client = SmsAliyunUtil.getInstance();
        //阿里云短信AccessKeyId
        String accessKeyId = systemConfigService.getValueByKey(Constants.CONFIG_KEY_SMS_ACCESSKEY_ID);
        // 阿里云短信SignName
        String smsSignName = systemConfigService.getValueByKey(Constants.CONFIG_KEY_SMS_SIGN_NAME);
        // 创建请求对象并设置入参
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName(smsSignName)
                .setPhoneNumbers(vo.getMobile())
                .setTemplateCode(vo.getTemplateKey()) // 阿里云短信模版CODE：SMS_463795422/模版：您的验证码为 ${code} ，该验证码5分钟内有效，请勿泄露于他人。
                .setTemplateParam(vo.getContent());// 阿里云短信参数{"code":"1234"}
        // 创建运行时配置对象 略
        try {
            // 发起请求
            // 运行时参数重试设置，仅对使用了该运行时参数实例的请求有效
            RuntimeOptions runtimeOptions = new RuntimeOptions();
            // 开启自动重试机制
            runtimeOptions.autoretry = true;
            // 设置自动重试次数
            runtimeOptions.maxAttempts = 3;

            SendSmsResponse resp = client.sendSmsWithOptions(sendSmsRequest, runtimeOptions);
            /**
             * statusCode
             * 200
             * body
             * {
             *   "Message": "OK",
             *   "RequestId": "E49A38B4-75D0-5CB4-8D67-C4F10853829E",
             *   "Code": "OK",
             *   "BizId": "168525139345227686^0"
             * }
             */
            if (Objects.nonNull(resp)) { // 成功
                // 发送记录
                SmsRecord smsRecord = new SmsRecord();
                smsRecord.setCreateTime(DateUtil.nowDateTime());
                smsRecord.setUid(accessKeyId);
                smsRecord.setTemplate(vo.getTemplateKey());
                smsRecord.setPhone(vo.getMobile());
                smsRecord.setContent(vo.getContent() + "_" + vo.getTemplateKey());
                smsRecord.setResultcode(resp.getStatusCode());
                smsRecord.setMemo(resp.getBody().getMessage() + "_" + resp.getBody().getBizId());
                smsRecordService.save(smsRecord);
            }
            return resp;
        } catch (Exception ex) {
            // 记录发送失败记录
            SmsRecord smsRecord = new SmsRecord();
            smsRecord.setCreateTime(DateUtil.nowDateTime());
            smsRecord.setUid(accessKeyId);
            smsRecord.setTemplate(vo.getTemplateKey());
            smsRecord.setPhone(vo.getMobile());
            smsRecord.setContent(vo.getContent() + "_" + vo.getTemplateKey());
            smsRecord.setResultcode(500);
            smsRecord.setMemo("阿里云短信发送失败：" + ex.getLocalizedMessage());
            smsRecordService.save(smsRecord);

            SendSmsResponse  errorResponse = new SendSmsResponse();
            errorResponse.setStatusCode(500);
            errorResponse.getBody().setCode("ERROR");
            errorResponse.getBody().setMessage("阿里云短信发送失败：" + ex.getLocalizedMessage());
            return errorResponse;
        }
    }
}
