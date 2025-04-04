package com.nbug.module.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbug.mico.common.model.product.StoreProductRule;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.request.StoreProductRuleRequest;
import com.nbug.mico.common.request.StoreProductRuleSearchRequest;

import java.util.List;

/**
 * StoreProductRuleService 接口

 */
public interface StoreProductRuleService extends IService<StoreProductRule> {

    /**
     * 商品规格列表
     * @param request 查询参数
     * @param pageParamRequest 分页参数
     * @return List
     */
    List<StoreProductRule> getList(StoreProductRuleSearchRequest request, PageParamRequest pageParamRequest);

    /**
     * 新增商品规格
     * @param storeProductRuleRequest 规格参数
     * @return 新增结果
     */
    boolean save(StoreProductRuleRequest storeProductRuleRequest);

    /**
     * 修改规格
     * @param storeProductRuleRequest 规格参数
     * @return Boolean
     */
    Boolean updateRule(StoreProductRuleRequest storeProductRuleRequest);
}
