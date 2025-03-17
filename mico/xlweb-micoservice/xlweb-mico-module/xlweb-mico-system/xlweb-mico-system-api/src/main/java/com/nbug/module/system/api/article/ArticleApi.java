package com.nbug.module.system.api.article;

import com.nbug.mico.common.model.article.Article;
import com.nbug.mico.common.pojo.CommonResult;
import com.nbug.module.system.enums.ApiConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = ApiConstants.NAME) // TODO NBUG：fallbackFactory =
@Tag(name = "RPC 服务 - 文章")
public interface ArticleApi {

    String PREFIX = ApiConstants.PREFIX + "/article";

    @GetMapping(PREFIX + "/getById")
    @Operation(summary = "获取文章ById")
    @Parameter(name = "id", description = "id", required = true)
    public CommonResult<Article> getById(@RequestParam Integer id);
}
