package com.nbug.module.system.controller.admin;

import com.nbug.mico.common.model.article.Article;
import com.nbug.mico.common.page.CommonPage;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.mico.common.request.ArticleRequest;
import com.nbug.mico.common.request.ArticleSearchRequest;
import com.nbug.mico.common.request.PageParamRequest;
import com.nbug.mico.common.vo.ArticleVo;
import com.nbug.module.system.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.nbug.mico.common.exception.enums.GlobalErrorCodeConstants.INTERNAL_SERVER_ERROR;


/**
 * 文章管理表 前端控制器
 
 */
@Slf4j
@RestController
@RequestMapping("api/admin/article")
@Tag(name = "文章管理")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 分页显示文章管理表
     * @param request ArticleSearchRequest 搜索条件
     * @param pageParamRequest 分页参数
     */
    @PreAuthorize("hasAuthority('admin:article:list')")
    @Operation(summary = "分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Parameter(name="keywords", description="搜索关键字")
    public CommonResult<CommonPage<ArticleVo>> getList(@Validated ArticleSearchRequest request,
                                                       @Validated PageParamRequest pageParamRequest) {
        return CommonResult.success(CommonPage.restPage(articleService.getAdminList(request, pageParamRequest)));
    }

    /**
     * 新增文章管理表
     * @param articleRequest 新增参数
     */
    @PreAuthorize("hasAuthority('admin:article:save')")
    @Operation(summary = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public CommonResult<String> save(@RequestBody @Validated ArticleRequest articleRequest) {
        if (articleService.create(articleRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 删除文章管理表
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:article:delete')")
    @Operation(summary = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @Parameter(name="id", description="文章ID")
    public CommonResult<String> delete(@RequestParam(value = "id") Integer id) {
        if (articleService.deleteById(id)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 修改文章管理表
     * @param id integer id
     * @param articleRequest 修改参数
     */
    @PreAuthorize("hasAuthority('admin:article:update')")
    @Operation(summary = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @Parameter(name="id", description="文章ID")
    public CommonResult<String> update(@RequestParam Integer id, @RequestBody @Validated ArticleRequest articleRequest) {
        if (articleService.updateArticle(id, articleRequest)) {
            return CommonResult.success("success");
        } else {
            return CommonResult.error(INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 查询文章管理表信息
     * @param id Integer
     */
    @PreAuthorize("hasAuthority('admin:article:info')")
    @Operation(summary = "详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @Parameter(name="id", description="文章ID")
    public CommonResult<Article> info(@RequestParam(value = "id") Integer id) {
        return CommonResult.success(articleService.getDetail(id));
   }
}



