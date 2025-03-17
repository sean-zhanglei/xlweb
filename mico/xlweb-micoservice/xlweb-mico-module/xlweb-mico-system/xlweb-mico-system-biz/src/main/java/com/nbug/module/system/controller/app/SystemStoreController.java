package com.nbug.module.system.controller.app;


import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreNearRequest;
import com.nbug.mico.common.response.StoreNearResponse;
import com.nbug.module.system.service.SystemStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提货点

 */
@Slf4j
@RestController("StoreController")
@RequestMapping("system/store")
@Tag(name = "应用后台 - 提货点")
public class SystemStoreController {
    @Autowired
    private SystemStoreService systemStoreService;

    /**
     * 附近的提货点
     */
    @Operation(summary = "附近的提货点")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public CommonResult<StoreNearResponse> register(@Validated StoreNearRequest request, @Validated PageParamRequest pageParamRequest){
        return CommonResult.success(systemStoreService.getNearList(request, pageParamRequest));
    }
}



