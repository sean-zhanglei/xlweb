package com.nbug.module.system.api.article;

import com.nbug.mico.common.model.article.Article;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.service.ArticleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ArticleApiImpl implements ArticleApi {

    @Resource
    private ArticleService articleService;
    @Override
    public CommonResult<Article> getById(Integer id) {
        return CommonResult.success(articleService.getById(id));
    }
}
