package com.nbug.module.system.service;


import com.nbug.mico.common.vo.LogisticsResultVo;

/**
* ExpressService 接口

*/
public interface LogisticService {
    LogisticsResultVo info(String expressNo, String type, String com, String phone);
}
