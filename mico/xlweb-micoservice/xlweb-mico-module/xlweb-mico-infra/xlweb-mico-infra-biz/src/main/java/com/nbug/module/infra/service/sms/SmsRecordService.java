package com.nbug.module.infra.service.sms;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.sms.SmsRecord;

/**
 * SmsRecordService 接口

 */
public interface SmsRecordService extends IService<SmsRecord> {

    /**
     * 保存短信记录
     * @param smsRecord 待保存短信记录
     * @return 保存结果
     */
    boolean save(SmsRecord smsRecord);

}
