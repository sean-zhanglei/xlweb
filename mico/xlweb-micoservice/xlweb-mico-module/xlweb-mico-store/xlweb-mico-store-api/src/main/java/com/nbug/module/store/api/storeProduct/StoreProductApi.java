package com.nbug.module.store.api.storeProduct;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreProductSearchRequest;
import com.nbug.mico.common.response.ProductActivityItemResponse;
import com.nbug.mico.common.response.StoreProductResponse;
import com.nbug.module.store.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;


@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 商品")
public interface StoreProductApi {

    String PREFIX = ApiConstants.PREFIX + "/storeProduct";

    @GetMapping(PREFIX + "/getListInIds")
    @Operation(summary = "根据id集合获取商品信息")
    @Parameter(name = "productIds", description = "商品Id列表", required = true)
    public CommonResult<List<StoreProduct>> getListInIds(List<Integer> productIds);

    @PostMapping(PREFIX + "/operationStock")
    @Operation(summary = "添加/扣减库存")
    @Parameters({
            @Parameter(name = "id", description = "商品Id", required = true),
            @Parameter(name = "num", description = "商品数量", required = true),
            @Parameter(name = "type", description = "操作类型", required = true)
    })
    public CommonResult<Boolean> operationStock(Integer id, Integer num, String type);

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "根据id获取商品信息")
    @Parameter(name = "id", description = "商品Id", required = true)
    public CommonResult<StoreProduct> getById(Integer id);


    @GetMapping(PREFIX + "/getSecondaryCategoryByProductId")
    @Operation(summary = "根据商品id获取二级分类")
    @Parameter(name = "productId", description = "商品Id", required = true)
    public CommonResult<List<Integer>> getSecondaryCategoryByProductId(String productId);

    @GetMapping(PREFIX + "/getAdminList")
    @Operation(summary = "获取商品列表")
    @Parameters({
            @Parameter(name = "request", description = "商品搜索请求", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<PageInfo<StoreProductResponse>> getAdminList(StoreProductSearchRequest request, PageParamRequest pageParamRequest);

    @GetMapping(PREFIX + "/getIndexProduct")
    @Operation(summary = "获取首页商品列表")
    @Parameters({
            @Parameter(name = "type", description = "类型 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】", required = true),
            @Parameter(name = "pageParamRequest", description = "分页参数", required = true)
    })
    public CommonResult<List<StoreProduct>> getIndexProduct(Integer type, PageParamRequest pageParamRequest);


    @GetMapping(PREFIX + "/getActivityByProduct")
    @Operation(summary = "根据商品参加的活动次序查找对应活动明细")
    @Parameters({
            @Parameter(name = "productId", description = "商品Id", required = true),
            @Parameter(name = "activity", description = "活动类型", required = true)
    })
    public CommonResult<HashMap<Integer, ProductActivityItemResponse>> getActivityByProduct(Integer productId, String activity);
}
