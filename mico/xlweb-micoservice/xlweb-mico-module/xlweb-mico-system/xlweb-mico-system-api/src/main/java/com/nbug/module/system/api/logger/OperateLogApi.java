package com.nbug.module.system.api.logger;

import com.nbug.mico.common.model.logger.OperateLog;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.pojo.PageResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 操作日志")
public interface OperateLogApi {

    String PREFIX = ApiConstants.PREFIX + "/operate-log";

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建操作日志")
    CommonResult<Boolean> createOperateLog(@Validated @RequestBody OperateLog createReqDTO);

    /**
     * 【异步】创建操作日志
     *
     * @param createReqDTO 请求
     */
    @PostMapping(PREFIX + "/create-async")
    @Operation(summary = "创建操作日志异步")
    public CommonResult<Boolean> createOperateLogAsync(@Validated @RequestBody OperateLog createReqDTO);

    @GetMapping(PREFIX + "/page")
    @Operation(summary = "获取指定模块的指定数据的操作日志分页")
    CommonResult<PageResult<OperateLog>> getOperateLogPage(@SpringQueryMap OperateLog pageReq);

}
