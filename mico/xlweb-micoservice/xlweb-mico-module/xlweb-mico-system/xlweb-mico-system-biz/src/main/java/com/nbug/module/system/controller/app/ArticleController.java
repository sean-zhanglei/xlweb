package com.nbug.module.system.controller.app;

import com.nbug.mico.common.model.article.Article;
import com.nbug.mico.common.model.category.Category;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.response.ArticleResponse;
import com.nbug.module.system.service.ArticleService;
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


/**
 * 文章

 */
@Slf4j
@RestController("ArticleFrontController")
@RequestMapping("system/article")
@Tag(name = "应用后台 - 文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页列表
     */
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleResponse>> getList(@PathVariable(name="cid") String cid,
                                                             @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(articleService.getList(cid, pageParamRequest)));
    }

    /**
     * 热门列表
     */
    @Operation(summary = "热门列表")
    @RequestMapping(value = "/hot/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleResponse>> getHotList() {
        return CommonResult.success(CommonPage.restPage(articleService.getHotList()));
    }

    /**
     * 轮播列表
     */
    @Operation(summary = "轮播列表")
    @RequestMapping(value = "/banner/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Article>> getList() {
        return CommonResult.success(CommonPage.restPage(articleService.getBannerList()));
    }

    /**
     * 文章分类列表
     */
    @Operation(summary = "文章分类列表")
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Category>> categoryList() {
        return CommonResult.success(CommonPage.restPage(articleService.getCategoryList()));
    }

    /**
     * 查询文章详情
     * @param id Integer
     */
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Parameter(name="id", description="文章ID")
    public CommonResult<ArticleResponse> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.getVoByFront(id));
   }
}



