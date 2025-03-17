package com.nbug.module.store.api.storeBargain;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.bargain.StoreBargain;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreBargainSearchRequest;
import com.nbug.mico.common.response.StoreBargainResponse;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 砍价")
public interface StoreBargainApi {

    String PREFIX = ApiConstants.PREFIX + "/storeBargain";


    @PostMapping(PREFIX + "/operationStock")
    @Operation(summary = "添加/扣减库存")
    @Parameters({
            @Parameter(name = "id", description = "商品Id", required = true),
            @Parameter(name = "num", description = "商品数量", required = true),
            @Parameter(name = "type", description = "操作类型", required = true)
    })
    public CommonResult<Boolean> operationStock(@RequestParam Integer id,
                                                @RequestParam Integer num,
                                                @RequestParam String type);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取商品信息")
    @Parameter(name = "id", description = "商品Id", required = true)
    public CommonResult<StoreBargain> getByIdException(@RequestParam Integer id);


    @GetMapping(PREFIX + "/getList")
    @Operation(summary = "获取砍价列表")
    @Parameters({
            @Parameter(name = "request", description = "查询参数", required = true),
            @Parameter(name = "pageParamReques", description = "分页参数", required = true)
    })
    public CommonResult<PageInfo<StoreBargainResponse>> getList(@Validated @RequestParam StoreBargainSearchRequest request,
                                                                @Validated @RequestParam PageParamRequest pageParamReques);
}
