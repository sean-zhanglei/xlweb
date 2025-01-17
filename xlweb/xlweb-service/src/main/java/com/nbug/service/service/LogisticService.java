package com.nbug.service.service;


import com.nbug.common.vo.LogisticsResultVo;

/**
* ExpressService 接口

*/
public interface LogisticService {
    LogisticsResultVo info(String expressNo, String type, String com, String phone);
}
