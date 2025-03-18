package com.nbug.module.store.controller.app;


import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.ProductListRequest;
import com.nbug.mico.common.request.ProductRequest;
import com.nbug.mico.common.response.IndexProductResponse;
import com.nbug.mico.common.response.OrderInfoResponse;
import com.nbug.mico.common.response.ProductDetailReplyResponse;
import com.nbug.mico.common.response.ProductDetailResponse;
import com.nbug.mico.common.response.ProductReplyResponse;
import com.nbug.mico.common.response.StoreProductReplayCountResponse;
import com.nbug.mico.common.vo.CategoryTreeVo;
import com.nbug.module.store.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户 -- 用户中心
 
 */
@Slf4j
@RestController("ProductController")
@RequestMapping("api/front/store/product")
@Tag(name = "应用后台 - 商品")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 热门商品推荐
     */
    @Operation(summary = "热门商品推荐")
    @RequestMapping(value = "/hot", method = RequestMethod.GET)
    public CommonResult<CommonPage<IndexProductResponse>> getHotProductList(@Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(productService.getHotProductList(pageParamRequest));
    }

    /**
     * 优选商品推荐
     */
    @Operation(summary = "优选商品推荐")
    @RequestMapping(value = "/good", method = RequestMethod.GET)
    public CommonResult<CommonPage<IndexProductResponse>> getGoodProductList() {
        return CommonResult.success(productService.getGoodProductList());
    }

    /**
     * 获取分类
     */
    @Operation(summary = "获取分类")
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public CommonResult<List<CategoryTreeVo>> getCategory() {
        return CommonResult.success(productService.getCategory());
    }

    /**
     * 商品列表
     */
    @Operation(summary = "商品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<IndexProductResponse>> getList(@Validated ProductRequest request, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(productService.getList(request, pageParamRequest));
    }

    /**
     * 商品购买记录 TOP 10
     * @return 订单购买记录
     */
    @Operation(summary = "商品购买记录")
    @RequestMapping(value = "/top10buy-list/{productId}", method = RequestMethod.GET)
    public CommonResult<List<OrderInfoResponse>> orderBuyListTop10(@PathVariable Integer productId) {
        return CommonResult.success(productService.orderBuyListTop10(productId));
    }

    /**
     * 商品详情
     */
    @Operation(summary = "商品详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @Parameter(name = "type", description = "normal-正常，video-视频")
    public CommonResult<ProductDetailResponse> getDetail(@PathVariable Integer id, @RequestParam(value = "type", defaultValue = "normal") String type) {
        return CommonResult.success(productService.getDetail(id, type));
    }

    /**
     * 商品评论列表
     */
    @Operation(summary = "商品评论列表")
    @RequestMapping(value = "/reply/list/{id}", method = RequestMethod.GET)
    @Parameter(name = "type", description = "评价等级|0=全部,1=好评,2=中评,3=差评")
    public CommonResult<CommonPage<ProductReplyResponse>> getReplyList(@PathVariable Integer id,
                                                                       @RequestParam(value = "type") Integer type, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(productService.getReplyList(id, type, pageParamRequest)));
    }

    /**
     * 商品评论数量
     */
    @Operation(summary = "商品评论数量")
    @RequestMapping(value = "/reply/config/{id}", method = RequestMethod.GET)
    public CommonResult<StoreProductReplayCountResponse> getReplyCount(@PathVariable Integer id) {
        return CommonResult.success(productService.getReplyCount(id));
    }

    /**
     * 商品详情评论
     */
    @Operation(summary = "商品详情评论")
    @RequestMapping(value = "/reply/product/{id}", method = RequestMethod.GET)
    public CommonResult<ProductDetailReplyResponse> getProductReply(@PathVariable Integer id) {
        return CommonResult.success(productService.getProductReply(id));
    }

    /**
     * 商品列表
     */
    @Operation(summary = "商品列表(个别分类模型使用)")
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<IndexProductResponse>> getProductList(@Validated ProductListRequest request, @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(productService.getCategoryProductList(request, pageParamRequest));
    }

    /**
     * 商品规格详情
     */
    @Operation(summary = "商品规格详情")
    @RequestMapping(value = "/sku/detail/{id}", method = RequestMethod.GET)
    public CommonResult<ProductDetailResponse> getSkuDetail(@PathVariable Integer id) {
        return CommonResult.success(productService.getSkuDetail(id));
    }

    /**
     * 商品排行榜
     */
    @Operation(summary = "商品排行榜")
    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public CommonResult<List<StoreProduct>> getLeaderboard() {
        return CommonResult.success(productService.getLeaderboard());
    }
}



