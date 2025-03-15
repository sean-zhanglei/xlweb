package com.nbug.module.store.api.storeSeckillManger;

import com.nbug.mico.common.model.seckill.StoreSeckillManger;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 秒杀管理")
public interface StoreSeckillMangerApi {

    String PREFIX = ApiConstants.PREFIX + "/storeSeckillManger";

    @PostMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取商品秒杀管理")
    @Parameter(name = "id", description = "Id", required = true)
    public CommonResult<StoreSeckillManger> getById(Integer id);
}
