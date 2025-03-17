package com.nbug.module.user.api.userLevel;

import com.nbug.mico.common.model.user.User;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户等级")
public interface UserLevelApi {

    String PREFIX = ApiConstants.PREFIX + "/level";

    @PostMapping(PREFIX + "/downLevel")
    @Operation(summary = "用户等级降级")
    @Parameter(name = "user", description = "用户", required = true)
    public CommonResult<Boolean> downLevel(@RequestParam User user);


    @PostMapping(PREFIX + "/upLevel")
    @Operation(summary = "用户等级升级")
    @Parameter(name = "user", description = "用户", required = true)
    public CommonResult<Boolean> upLevel(@RequestParam User user);


    @PostMapping(PREFIX + "/deleteByLevelId")
    @Operation(summary = "删除用户等级")
    @Parameter(name = "levelId", description = "用户等级id", required = true)
    public CommonResult<Boolean> deleteByLevelId(@RequestParam Integer levelId);
}
