package com.nbug.module.infra.service.wechat.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.constants.Constants;
import com.nbug.mico.common.model.wechat.WechatExceptions;
import com.nbug.module.infra.dal.WechatExceptionsDao;
import com.nbug.module.infra.service.wechat.WechatExceptionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 *  微信异常服务实现类
 
 */
@Service
public class WechatExceptionsServiceImpl extends ServiceImpl<WechatExceptionsDao, WechatExceptions> implements WechatExceptionsService {

    @Resource
    private WechatExceptionsDao dao;

    /**
     * 删除历史日志
     */
    @Override
    public void autoDeleteLog() {
        String beforeDate = DateUtil.offsetDay(new Date(), -9).toString(Constants.DATE_FORMAT_DATE);
        UpdateWrapper<WechatExceptions> wrapper = Wrappers.update();
        wrapper.lt("create_time", beforeDate);
        dao.delete(wrapper);
    }
}

