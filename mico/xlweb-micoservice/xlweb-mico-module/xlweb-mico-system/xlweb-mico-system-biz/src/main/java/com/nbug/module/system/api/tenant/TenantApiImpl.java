package com.nbug.module.system.api.tenant;

import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.tenant.TenantService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;


@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class TenantApiImpl implements TenantApi {

    @Resource
    private TenantService tenantService;

    @Override
    public CommonResult<List<Long>> getTenantIdList() {
//        return success(tenantService.getTenantIdList());
        return success(new ArrayList<>());
    }

    @Override
    public CommonResult<Boolean> validTenant(Long id) {
//        tenantService.validTenant(id);
        return success(true);
    }

}
