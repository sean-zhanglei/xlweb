package com.nbug.module.store.api.storeBargainUser;

import com.nbug.mico.common.model.bargain.StoreBargainUser;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户参与砍价")
public interface StoreBargainUserApi {

    String PREFIX = ApiConstants.PREFIX + "/storeBargainUser";

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取ById")
    @Parameter(name = "id", description = "Id", required = true)
    public CommonResult<StoreBargainUser> getById(Integer id);

}
