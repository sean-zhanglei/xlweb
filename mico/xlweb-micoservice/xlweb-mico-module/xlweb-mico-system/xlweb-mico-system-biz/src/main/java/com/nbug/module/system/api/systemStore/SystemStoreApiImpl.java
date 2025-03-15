package com.nbug.module.system.api.systemStore;

import com.nbug.mico.common.model.system.SystemStore;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.SystemStoreService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class SystemStoreApiImpl implements SystemStoreApi {

    @Resource
    private SystemStoreService systemStoreService;


    /**
     * 根据基本参数获取
     * @param systemStore 基本参数
     * @return 门店自提结果
     */
    @Override
    public CommonResult<SystemStore> getByCondition(SystemStore systemStore) {
        return CommonResult.success(systemStoreService.getByCondition(systemStore));
    }

    /**
     * 根据id获取门店自提信息
     * @param storeIdList
     * @return
     */
    @Override
    public CommonResult<HashMap<Integer, SystemStore>> getMapInId(List<Integer> storeIdList) {
        return CommonResult.success(systemStoreService.getMapInId(storeIdList));
    }

    @Override
    public CommonResult<SystemStore> getById(Integer id) {
        return CommonResult.success(systemStoreService.getById(id));
    }
}
