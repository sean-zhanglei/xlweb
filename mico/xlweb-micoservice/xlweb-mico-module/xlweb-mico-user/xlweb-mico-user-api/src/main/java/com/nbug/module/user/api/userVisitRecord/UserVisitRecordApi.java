package com.nbug.module.user.api.userVisitRecord;

import com.nbug.mico.common.model.record.UserVisitRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户访问记录")
public interface UserVisitRecordApi {

    String PREFIX = ApiConstants.PREFIX + "/visit";


    @PostMapping(PREFIX + "/save")
    @Operation(summary = "保存用户访问记录")
    @Parameter(name = "userVisitRecord", description = "用户访问记录", required = true)
    public CommonResult<Boolean> save(@RequestParam UserVisitRecord userVisitRecord);

    @GetMapping(PREFIX + "/getPageviewsByDate")
    @Operation(summary = "通过日期获取浏览量")
    @Parameter(name = "date", description = "日期", required = true)
    public CommonResult<Integer> getPageviewsByDate(@RequestParam String date);

    @GetMapping(PREFIX + "/getActiveUserNumByDate")
    @Operation(summary = "通过日期获取活跃用户数")
    @Parameter(name = "date", description = "日期", required = true)
    public CommonResult<Integer> getActiveUserNumByDate(@RequestParam String date);

}
