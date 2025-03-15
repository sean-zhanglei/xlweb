package com.nbug.module.store.api.storeCart;

import com.nbug.mico.common.model.cat.StoreCart;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.store.service.StoreCartService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreCartApiImpl implements StoreCartApi {

    @Resource
    private StoreCartService storeCartService;

    /**
     * 删除购物车
     * @param ids 待删除id
     * @return 返回删除状态
     */
    @Override
    public CommonResult<Boolean> deleteCartByIds(List<Long> ids) {
        return success(storeCartService.deleteCartByIds(ids));
    }

    /**
     * 通过id和uid获取购物车信息
     * @param id 购物车id
     * @param uid 用户uid
     * @return StoreCart
     */
    @Override
    public CommonResult<StoreCart> getByIdAndUid(Long id, Integer uid) {
        return success(storeCartService.getByIdAndUid(id, uid));
    }

}
