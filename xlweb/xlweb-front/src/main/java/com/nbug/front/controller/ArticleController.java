package com.nbug.front.controller;

import com.nbug.common.page.CommonPage;
import com.nbug.common.response.ArticleResponse;
import com.nbug.common.response.CommonResult;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.model.article.Article;
import com.nbug.common.model.category.Category;
import com.nbug.service.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 文章

 */
@Slf4j
@RestController("ArticleFrontController")
@RequestMapping("api/front/article")
@Api(tags = "文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页列表
     */
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleResponse>> getList(@PathVariable(name="cid") String cid,
                                                             @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(articleService.getList(cid, pageParamRequest)));
    }

    /**
     * 热门列表
     */
    @ApiOperation(value = "热门列表")
    @RequestMapping(value = "/hot/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<ArticleResponse>> getHotList() {
        return CommonResult.success(CommonPage.restPage(articleService.getHotList()));
    }

    /**
     * 轮播列表
     */
    @ApiOperation(value = "轮播列表")
    @RequestMapping(value = "/banner/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Article>> getList() {
        return CommonResult.success(CommonPage.restPage(articleService.getBannerList()));
    }

    /**
     * 文章分类列表
     */
    @ApiOperation(value = "文章分类列表")
    @RequestMapping(value = "/category/list", method = RequestMethod.GET)
    public CommonResult<CommonPage<Category>> categoryList() {
        return CommonResult.success(CommonPage.restPage(articleService.getCategoryList()));
    }

    /**
     * 查询文章详情
     * @param id Integer
     */
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiImplicitParam(name="id", value="文章ID")
    public CommonResult<ArticleResponse> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.getVoByFront(id));
   }
}



