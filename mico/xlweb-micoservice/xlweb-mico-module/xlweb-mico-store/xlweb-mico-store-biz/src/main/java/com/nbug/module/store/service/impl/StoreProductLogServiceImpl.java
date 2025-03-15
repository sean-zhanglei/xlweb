package com.nbug.module.store.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbug.mico.common.model.log.StoreProductLog;
import com.nbug.module.store.dal.StoreProductLogDao;
import com.nbug.module.store.service.StoreProductLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * StoreProductLogServiceImpl 接口实现

 */
@Service
public class StoreProductLogServiceImpl extends ServiceImpl<StoreProductLogDao, StoreProductLog> implements StoreProductLogService {

    @Resource
    private StoreProductLogDao dao;

}

