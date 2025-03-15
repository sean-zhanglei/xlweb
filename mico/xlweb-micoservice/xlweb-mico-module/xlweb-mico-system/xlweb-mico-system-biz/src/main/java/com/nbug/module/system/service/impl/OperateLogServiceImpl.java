package com.nbug.module.system.service.impl;

import com.nbug.mico.common.model.logger.OperateLog;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.module.system.dal.OperateLogDao;
import com.nbug.module.system.service.OperateLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * 操作日志 Service 实现类
 *
 * @author NUBG
 */
@Service
@Validated
@Slf4j
public class OperateLogServiceImpl implements OperateLogService {

    @Resource
    private OperateLogDao operateLogDao;

    @Override
    public void createOperateLog(OperateLog createReqDTO) {
        operateLogDao.insert(createReqDTO);
    }


    @Override
    public PageResult<OperateLog> getOperateLogPage(OperateLog pageReqDTO) {
        return operateLogDao.selectPage(pageReqDTO);
    }

}
