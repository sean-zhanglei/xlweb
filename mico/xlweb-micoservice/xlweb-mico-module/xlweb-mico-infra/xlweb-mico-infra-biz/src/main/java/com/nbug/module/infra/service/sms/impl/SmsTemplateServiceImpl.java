package com.nbug.module.infra.service.sms.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.exception.XlwebException;
import com.nbug.mico.common.model.sms.SmsTemplate;
import com.nbug.module.infra.dal.SmsTemplateDao;
import com.nbug.module.infra.service.sms.SmsTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * SmsTemplateServiceImpl 接口实现

 */
@Service
public class SmsTemplateServiceImpl extends ServiceImpl<SmsTemplateDao, SmsTemplate> implements SmsTemplateService {

    @Resource
    private SmsTemplateDao dao;

    /**
     * 获取详情
     * @param id 模板id
     * @return SmsTemplate
     */
    @Override
    public SmsTemplate getDetail(Integer id) {
        SmsTemplate smsTemplate = getById(id);
        if (ObjectUtil.isNull(smsTemplate)) {
            throw new XlwebException("短信模板不存在");
        }
        return smsTemplate;
    }
}

