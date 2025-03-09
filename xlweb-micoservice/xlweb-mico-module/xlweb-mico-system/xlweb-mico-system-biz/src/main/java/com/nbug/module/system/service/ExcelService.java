package com.nbug.module.system.module.system.service;

import com.nbug.common.request.StoreBargainSearchRequest;
import com.nbug.common.request.StoreCombinationSearchRequest;
import com.nbug.common.request.StoreOrderSearchRequest;
import com.nbug.common.request.StoreProductSearchRequest;

/**
* StoreProductService 接口

*/
public interface ExcelService{

    /**
     * 导出砍价商品
     * @param request 请求参数
     * @return 导出地址
     */
    String exportBargainProduct(StoreBargainSearchRequest request);

    /**
     * 导出拼团商品
     * @param request 请求参数
     * @return 导出地址
     */
    String exportCombinationProduct(StoreCombinationSearchRequest request);

    /**
     * 商品导出
     * @param request 请求参数
     * @return 导出地址
     */
    String exportProduct(StoreProductSearchRequest request);

    /**
     * 订单导出
     * @param request 查询条件
     * @return 文件名称
     */
    String exportOrder(StoreOrderSearchRequest request);
}
