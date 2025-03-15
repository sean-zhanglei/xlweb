package com.nbug.module.store.api.storeSeckill;

import com.nbug.mico.common.model.seckill.StoreSeckill;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = ApiConstants.NAME) // TODO 芋艿：fallbackFactory =
@Tag(name = "RPC 服务 - 秒杀商品")
public interface StoreSeckillApi {

    String PREFIX = ApiConstants.PREFIX + "/storeSeckill";


    @PostMapping(PREFIX + "/operationStock")
    @Operation(summary = "添加/扣减库存")
    @Parameters({
            @Parameter(name = "id", description = "商品Id", required = true),
            @Parameter(name = "num", description = "商品数量", required = true),
            @Parameter(name = "type", description = "操作类型", required = true)
    })
    public CommonResult<Boolean> operationStock(Integer id, Integer num, String type);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取商品信息")
    @Parameter(name = "id", description = "商品Id", required = true)
    public CommonResult<StoreSeckill> getByIdException(Integer id);
}
