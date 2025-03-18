package com.nbug.module.system.service;

import com.nbug.mico.common.model.logger.OperateLog;
import com.nbug.mico.common.pojo.PageResult;

/**
 * 操作日志 Service 接口
 *
 * @author NUBG
 */
public interface OperateLogService {

    /**
     * 记录操作日志
     *
     * @param createReqDTO 创建请求
     */
    void createOperateLog(OperateLog createReqDTO);


    /**
     * 记录操作日志异步
     *
     * @param createReqDTO 创建请求
     */
    void createOperateLogAsync(OperateLog createReqDTO);

    /**
     * 获得操作日志分页列表
     *
     * @param pageReqVO 分页条件
     * @return 操作日志分页列表
     */
    PageResult<OperateLog> getOperateLogPage(OperateLog pageReqVO);
}
