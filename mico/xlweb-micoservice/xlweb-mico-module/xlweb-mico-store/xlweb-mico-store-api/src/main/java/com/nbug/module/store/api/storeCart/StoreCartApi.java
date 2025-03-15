package com.nbug.module.store.api.storeCart;

import com.nbug.mico.common.model.cat.StoreCart;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 购物车")
public interface StoreCartApi {

    String PREFIX = ApiConstants.PREFIX + "/storeCart";


    @PostMapping(PREFIX + "/deleteCartByIds")
    @Operation(summary = "批量删除购物车")
    @Parameter(name = "ids", description = "购物车id集合", required = true)
    public CommonResult<Boolean> deleteCartByIds(List<Long> ids);


    @GetMapping(PREFIX + "/getByIdAndUid")
    @Operation(summary = "通过id和uid获取购物车信息")
    @Parameters({
            @Parameter(name = "id", description = "购物车id", required = true),
            @Parameter(name = "uid", description = "用户id", required = true)
    })
    public CommonResult<StoreCart> getByIdAndUid(Long id, Integer uid);

}
