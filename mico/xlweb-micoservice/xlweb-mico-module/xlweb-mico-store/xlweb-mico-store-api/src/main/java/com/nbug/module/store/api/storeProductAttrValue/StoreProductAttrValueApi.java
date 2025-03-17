package com.nbug.module.store.api.storeProductAttrValue;

import com.nbug.mico.common.model.product.StoreProductAttrValue;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 商品属性值")
public interface StoreProductAttrValueApi {

    String PREFIX = ApiConstants.PREFIX + "/storeProductAttrValue";

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取商品属性值")
    @Parameter(name = "id", description = "商品属性值Id", required = true)
    public CommonResult<StoreProductAttrValue> getById(@RequestParam Integer id);

    @GetMapping(PREFIX + "/getByProductIdAndSkuAndType")
    @Operation(summary = "根据商品id、sku、类型获取商品属性值")
    @Parameter(name = "productId", description = "商品Id", required = true)
    public CommonResult<StoreProductAttrValue> getByProductIdAndSkuAndType(@RequestParam Integer productId,
                                                                           @RequestParam String suk,
                                                                           @RequestParam Integer type);


    @GetMapping(PREFIX + "/getByIdAndProductIdAndType")
    @Operation(summary = "根据商品属性值id、商品id、类型获取商品属性值")
    @Parameter(name = "id", description = "商品属性值Id", required = true)
    public CommonResult<StoreProductAttrValue> getByIdAndProductIdAndType(@RequestParam Integer id,
                                                                          @RequestParam Integer productId,
                                                                          @RequestParam Integer type);


    @GetMapping(PREFIX + "/operationStock")
    @Operation(summary = "添加/扣减库存")
    @Parameters({
            @Parameter(name = "id", description = "商品属性值Id", required = true),
            @Parameter(name = "num", description = "商品数量", required = true),
            @Parameter(name = "operationType", description = "操作类型", required = true),
            @Parameter(name = "type", description = "类型", required = true)
    })
    public CommonResult<Boolean> operationStock(@RequestParam Integer id,
                                                @RequestParam Integer num,
                                                @RequestParam String operationType,
                                                @RequestParam Integer type);
}
