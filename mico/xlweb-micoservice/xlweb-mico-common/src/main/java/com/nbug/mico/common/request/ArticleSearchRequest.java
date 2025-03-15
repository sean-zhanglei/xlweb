package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * 文章管理 搜索Request

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="文章管理表")
public class ArticleSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "分类id")
    private String cid;

    @Schema(description = "搜索关键字")
    private String keywords;

}
