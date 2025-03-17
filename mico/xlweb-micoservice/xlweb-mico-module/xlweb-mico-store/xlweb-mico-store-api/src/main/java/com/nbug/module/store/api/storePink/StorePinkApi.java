package com.nbug.module.store.api.storePink;

import com.nbug.mico.common.model.combination.StorePink;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 拼团")
public interface StorePinkApi {

    String PREFIX = ApiConstants.PREFIX + "/storePink";

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取ById")
    @Parameter(name = "id", description = "Id", required = true)
    public CommonResult<StorePink> getById(@RequestParam Integer id);

    @GetMapping(PREFIX + "/getListByCidAndKid")
    @Operation(summary = "获取ById")
    @Parameters({
            @Parameter(name = "cid", description = "商品Id", required = true),
            @Parameter(name = "kid", description = "拼团Id", required = true)
    })
    public CommonResult<List<StorePink>> getListByCidAndKid(@RequestParam Integer cid,
                                                            @RequestParam Integer kid);

    @PostMapping(PREFIX + "/updateBatchById")
    @Operation(summary = "批量更新ById")
    @Parameter(name = "storePinks", description = "拼团信息", required = true)
    public CommonResult<Boolean> updateBatchById(@RequestParam List<StorePink> storePinks);


    @GetMapping(PREFIX + "/getCountByKid")
    @Operation(summary = "获取ById")
    @Parameter(name = "pinkId", description = "拼团Id", required = true)
    public CommonResult<Integer> getCountByKid(@RequestParam Integer pinkId);

    @PostMapping(PREFIX + "/save")
    @Operation(summary = "保存")
    @Parameter(name = "storePink", description = "拼团信息", required = true)
    public CommonResult<Boolean> save(@RequestParam StorePink storePink);
}
