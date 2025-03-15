package com.nbug.module.store.api.storeProductAttrValue;

import com.nbug.mico.common.model.product.StoreProductAttrValue;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreProductAttrValueService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreProductAttrValueApiImpl implements StoreProductAttrValueApi {

    @Resource
    private StoreProductAttrValueService storeProductAttrValueService;

    @Override
    public CommonResult<StoreProductAttrValue> getById(Integer id) {
        return success(storeProductAttrValueService.getById(id));
    }

    /**
     * 根据sku查询
     * @param productId 商品id
     * @param suk   sku
     * @param type  规格类型
     * @return StoreProductAttrValue
     */
    @Override
    public CommonResult<StoreProductAttrValue> getByProductIdAndSkuAndType(Integer productId, String suk, Integer type) {
        return success(storeProductAttrValueService.getByProductIdAndSkuAndType(productId, suk, type));
    }

    /**
     * 根据id、类型查询
     * @param id ID
     * @param type 类型
     * @return StoreProductAttrValue
     */
    @Override
    public CommonResult<StoreProductAttrValue> getByIdAndProductIdAndType(Integer id, Integer productId, Integer type) {
        return success(storeProductAttrValueService.getByIdAndProductIdAndType(id, productId, type));
    }

    /**
     * 添加/扣减库存
     * @param id 秒杀商品id
     * @param num 数量
     * @param operationType 类型：add—添加，sub—扣减
     * @param type 活动类型 0=商品，1=秒杀，2=砍价，3=拼团
     */
    @Override
    public CommonResult<Boolean> operationStock(Integer id, Integer num, String operationType, Integer type) {
        return success(storeProductAttrValueService.operationStock(id, num, operationType, type));
    }
}
