package com.nbug.admin.controller;

import com.nbug.common.model.article.Article;
import com.nbug.common.page.CommonPage;
import com.nbug.common.request.ArticleRequest;
import com.nbug.common.request.ArticleSearchRequest;
import com.nbug.common.request.PageParamRequest;
import com.nbug.common.response.CommonResult;
import com.nbug.common.vo.ArticleVo;
import com.nbug.service.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 文章管理表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/article")
@Api(tags = "文章管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页显示文章管理表
     * @param request ArticleSearchRequest 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:article:list')")
    @ApiOperation(value = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiImplicitParam(name="keywords", value="搜索关键字")
    public CommonResult<CommonPage<ArticleVo>> getList(@Validated ArticleSearchRequest request,
                                                       @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(articleService.getAdminList(request, pageParamRequest)));
    }

    /**
     * 新增文章管理表
     * @param articleRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:article:save')")
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated ArticleRequest articleRequest) {
        if (articleService.create(articleRequest)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 删除文章管理表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:article:delete')")
    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ApiImplicitParam(name="id", value="文章ID")
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (articleService.deleteById(id)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 修改文章管理表
     * @param id integer id
     * @param articleRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:article:update')")
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiImplicitParam(name="id", value="文章ID")
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated ArticleRequest articleRequest) {
        if (articleService.updateArticle(id, articleRequest)) {
            return CommonResult.success();
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 查询文章管理表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:article:info')")
    @ApiOperation(value = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiImplicitParam(name="id", value="文章ID")
    public CommonResult<Article> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.getDetail(id));
   }
}



