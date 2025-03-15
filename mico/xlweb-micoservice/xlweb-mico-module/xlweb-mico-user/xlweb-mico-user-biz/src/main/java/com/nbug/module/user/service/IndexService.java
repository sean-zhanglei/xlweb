package com.nbug.module.user.service;


import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.IndexInfoResponse;
import com.nbug.mico.common.response.IndexProductResponse;
import com.nbug.mico.common.vo.MyRecord;

import java.util.HashMap;
import java.util.List;

/**
* IndexService 接口

*/
public interface IndexService {

    /**
     * 首页信息
     * @return IndexInfoResponse
     */
    IndexInfoResponse getIndexInfo();

    /**
     * 热门搜索
     * @return List
     */
    List<HashMap<String, Object>> hotKeywords();

    /**
     * 分享配置信息
     */
    HashMap<String, String> getShareConfig();

    /**
     * 获取首页商品列表
     * @param type 类型 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】
     * @param pageParamRequest 分页参数
     * @return List
     */
    CommonPage<IndexProductResponse> findIndexProductList(Integer type, PageParamRequest pageParamRequest);

    /**
     * 获取颜色配置
     * @return SystemConfig
     */
    SystemConfig getColorConfig();

    /**
     * 获取版本信息
     * @return MyRecord
     */
    MyRecord getVersion();

    /**
     * 获取全局本地图片域名
     * @return String
     */
    String getImageDomain();
}
