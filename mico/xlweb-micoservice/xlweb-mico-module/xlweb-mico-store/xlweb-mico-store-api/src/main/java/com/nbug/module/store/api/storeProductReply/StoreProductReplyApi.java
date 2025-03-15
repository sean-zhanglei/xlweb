package com.nbug.module.store.api.storeProductReply;

import com.nbug.mico.common.model.product.StoreProductReply;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.StoreProductReplyAddRequest;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 评论")
public interface StoreProductReplyApi {

    String PREFIX = ApiConstants.PREFIX + "/storeProductReply";

    @PostMapping(PREFIX + "/saveBatch")
    @Operation(summary = "批量保存评论")
    @Parameter(name = "replyLists", description = "回复列表", required = true)
    public CommonResult<Boolean> saveBatch(List<StoreProductReply> replyLists);

    @PostMapping(PREFIX + "/create")
    @Operation(summary = "创建评论")
    @Parameter(name = "request", description = "评论", required = true)
    public CommonResult<Boolean> create( StoreProductReplyAddRequest request);

}
