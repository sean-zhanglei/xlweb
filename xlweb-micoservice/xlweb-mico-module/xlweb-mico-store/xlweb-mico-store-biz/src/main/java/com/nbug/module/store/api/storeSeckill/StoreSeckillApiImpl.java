package com.nbug.module.store.api.storeBargain;

import com.nbug.mico.common.model.bargain.StoreBargain;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreBargainService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreBargainApiImpl implements StoreBargainApi {

    @Resource
    private StoreBargainService storeBargainService;


    /**
     * 添加/扣减库存
     * @param id 秒杀商品id
     * @param num 数量
     * @param type 类型：add—添加，sub—扣减
     */
    @Override
    public CommonResult<Boolean> operationStock(Integer id, Integer num, String type) {
        return success(storeBargainService.operationStock(id, num, type));
    }

    /**
     * 查询带异常
     * @param id 砍价商品id
     * @return StoreBargain
     */

    @Override
    public CommonResult<StoreBargain> getByIdException(Integer id) {
        return success(storeBargainService.getByIdException(id));
    }
}
