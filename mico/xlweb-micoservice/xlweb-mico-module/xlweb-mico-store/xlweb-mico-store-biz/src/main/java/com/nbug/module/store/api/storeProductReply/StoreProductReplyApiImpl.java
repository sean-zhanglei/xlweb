package com.nbug.module.store.api.storeProductReply;

import com.nbug.mico.common.model.product.StoreProductReply;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.StoreProductReplyAddRequest;
import com.nbug.module.store.service.StoreProductReplyService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreProductReplyApiImpl implements StoreProductReplyApi {

    @Resource
    private StoreProductReplyService storeProductReplyService;

    @Override
    public CommonResult<Boolean> saveBatch(List<StoreProductReply> replyLists) {
        return success(storeProductReplyService.saveBatch(replyLists));
    }

    /**
     * 创建订单商品评价
     * @param request 请求参数
     * @return Boolean
     */
    @Override
    public CommonResult<Boolean> create( StoreProductReplyAddRequest request) {
        return success(storeProductReplyService.create(request));
    }
}
