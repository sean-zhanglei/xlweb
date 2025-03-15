package com.nbug.module.user.controller.app;


import com.nbug.mico.common.model.system.SystemConfig;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.IndexInfoResponse;
import com.nbug.mico.common.response.IndexProductResponse;
import com.nbug.module.user.service.IndexService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 
 */
@Slf4j
@RestController("IndexController")
@RequestMapping("api/front/user/index")
@Tag(name = "首页")
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 首页数据
     */
    @Operation(summary = "首页数据")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public CommonResult<IndexInfoResponse> getIndexInfo() {
        return CommonResult.success(indexService.getIndexInfo());
    }

    /**
     * 首页商品列表
     */
    @Operation(summary = "首页商品列表")
    @RequestMapping(value = "/product/{type}", method = RequestMethod.GET)
    @Parameter(name = "type", description = "类型 【1 精品推荐 2 热门榜单 3首发新品 4促销单品】")
    public CommonResult<CommonPage<IndexProductResponse>> getProductList(@PathVariable(value = "type") Integer type, PageParamRequest pageParamRequest) {

        return CommonResult.success(indexService.findIndexProductList(type, pageParamRequest));
    }

    /**
     * 热门搜索
     */
    @Operation(summary = "热门搜索")
    @RequestMapping(value = "/search/keyword", method = RequestMethod.GET)
    public CommonResult<List<HashMap<String, Object>>> hotKeywords() {
        return CommonResult.success(indexService.hotKeywords());
    }

    /**
     * 分享配置
     */
    @Operation(summary = "分享配置")
    @RequestMapping(value = "/share", method = RequestMethod.GET)
    public CommonResult<HashMap<String, String>> share() {
        return CommonResult.success(indexService.getShareConfig());
    }

    /**
     * 颜色配置
     */
    @Operation(summary = "颜色配置")
    @RequestMapping(value = "/color/config", method = RequestMethod.GET)
    public CommonResult<SystemConfig> getColorConfig() {
        return CommonResult.success(indexService.getColorConfig());
    }

    /**
     * 版本信息
     */
    @Operation(summary = "获取版本信息")
    @RequestMapping(value = "/get/version", method = RequestMethod.GET)
    public CommonResult<Map<String, Object>> getVersion() {
        return CommonResult.success(indexService.getVersion().getColumns());
    }

    /**
     * 全局本地图片域名
     */
    @Operation(summary = "全局本地图片域名")
    @RequestMapping(value = "/image/domain", method = RequestMethod.GET)
    public CommonResult<String> getImageDomain() {
        return CommonResult.success(indexService.getImageDomain());
    }
}



