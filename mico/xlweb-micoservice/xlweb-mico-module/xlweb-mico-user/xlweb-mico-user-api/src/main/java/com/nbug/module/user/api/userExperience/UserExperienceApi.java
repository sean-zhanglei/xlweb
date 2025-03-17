package com.nbug.module.user.api.userExperience;

import com.nbug.mico.common.model.user.UserExperienceRecord;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.user.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 用户经验")
public interface UserExperienceApi {

    String PREFIX = ApiConstants.PREFIX + "/experience";

    @GetMapping(PREFIX + "/getByOrderNoAndUid")
    @Operation(summary = "通过订单编号获取经验记录")
    @Parameters({
            @Parameter(name = "orderNo", description = "订单号", required = true),
            @Parameter(name = "uid", description = "用户id", required = true)
    })
    public CommonResult<UserExperienceRecord> getByOrderNoAndUid(@RequestParam String orderNo,
                                                                 @RequestParam Integer uid);

    @PostMapping(PREFIX + "/save")
    @Operation(summary = "保存用户经验记录")
    @Parameter(name = "userExperienceRecord", description = "用户经验记录", required = true)
    public CommonResult<Boolean> save(@RequestParam UserExperienceRecord userExperienceRecord);

}
