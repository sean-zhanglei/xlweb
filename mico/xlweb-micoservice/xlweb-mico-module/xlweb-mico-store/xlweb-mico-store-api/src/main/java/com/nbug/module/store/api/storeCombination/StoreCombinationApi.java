package com.nbug.module.store.api.storeCombination;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.combination.StoreCombination;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreCombinationSearchRequest;
import com.nbug.mico.common.response.StoreCombinationResponse;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 拼团商品")
public interface StoreCombinationApi {

    String PREFIX = ApiConstants.PREFIX + "/storeCombination";

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

    @PostMapping(PREFIX + "/getByIdException")
    @Operation(summary = "根据id获取拼团商品信息")
    @Parameter(name = "id", description = "商品Id", required = true)
    public CommonResult<StoreCombination> getByIdException(@RequestParam Integer combinationId);

    @PostMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取拼团商品信息")
    @Parameter(name = "id", description = "商品Id", required = true)
    public CommonResult<StoreCombination> getById(@RequestParam Integer id);

    @PostMapping(PREFIX + "/getList")
    @Operation(summary = "获取拼团商品列表")
    @Parameters({
            @Parameter(name = "request", description = "查询参数", required = true),
            @Parameter(name = "pageParamReques", description = "分页参数", required = true)
    })
    public CommonResult<PageInfo<StoreCombinationResponse>> getList(@Validated @RequestParam StoreCombinationSearchRequest request,
                                                                    @Validated @RequestParam PageParamRequest pageParamRequest);

}
