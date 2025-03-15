package com.nbug.module.store.api.storeProduct;

import com.github.pagehelper.PageInfo;
import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreProductSearchRequest;
import com.nbug.mico.common.response.ProductActivityItemResponse;
import com.nbug.mico.common.response.StoreProductResponse;
import com.nbug.module.store.service.StoreProductService;
import com.nbug.module.store.util.delete.ProductUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

import static com.nbug.mico.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class StoreProductApiImpl implements StoreProductApi {

    @Resource
    private StoreProductService storeProductService;

    @Resource
    private ProductUtils productUtils;

    /**
     * 根据id集合获取商品信息
     * @param productIds id集合
     * @return 商品信息
     */
    @Override
    public CommonResult<List<StoreProduct>> getListInIds(List<Integer> productIds) {
        return success(storeProductService.getListInIds(productIds));
    }

    /**
     * 添加/扣减库存
     * @param id 商品id
     * @param num 数量
     * @param type 类型：add—添加，sub—扣减
     */
    @Override
    public CommonResult<Boolean> operationStock(Integer id, Integer num, String type) {
        return success(storeProductService.operationStock(id, num, type));
    }

    @Override
    public CommonResult<StoreProduct> getById(Integer id) {
        return success(storeProductService.getById(id));
    }

    /**
     * 根据商品id取出二级分类
     * @param productId String 商品分类
     * @return List<Integer>
     */
    @Override
    public CommonResult<List<Integer>> getSecondaryCategoryByProductId(String productId) {
        return success(storeProductService.getSecondaryCategoryByProductId(productId));
    }

    /**
     * 获取产品列表Admin
     * @param request 筛选参数
     * @param pageParamRequest 分页参数
     * @return PageInfo
     */
    @Override
    public CommonResult<PageInfo<StoreProductResponse>> getAdminList(StoreProductSearchRequest request, PageParamRequest pageParamRequest) {
        return success(storeProductService.getAdminList(request, pageParamRequest));
    }

    /**
     * 首页商品列表
     * @param type 类型 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】
     * @param pageParamRequest 分页参数
     * @return CommonPage
     */
    @Override
    public CommonResult<List<StoreProduct>> getIndexProduct(Integer type, PageParamRequest pageParamRequest) {
        return success(storeProductService.getIndexProduct(type, pageParamRequest));
    }

    /**
     * 根据商品参加的活动次序查找对应活动明细
     * @param productId 商品id
     * @param activity 活动次序
     * @return 活动结果
     */
    @Override
    public CommonResult<HashMap<Integer, ProductActivityItemResponse>> getActivityByProduct(Integer productId, String activity) {
        return success(productUtils.getActivityByProduct(productId, activity));
    }
}
