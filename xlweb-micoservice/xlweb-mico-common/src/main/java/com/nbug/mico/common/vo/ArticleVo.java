package com.nbug.mico.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理 Vo
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="文章管理表")
public class ArticleVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "文章管理ID")
    private Integer id;

    @Schema(description = "分类id")
    private String cid;

    @Schema(description = "文章标题")
    private String title;

    @Schema(description = "文章作者")
    private String author;

    @Schema(description = "文章图片")
    private String imageInput;

    @Schema(description = "文章简介")
    private String synopsis;

    @Schema(description = "浏览次数")
    private String visit;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone="GMT+8")
    private Date updateTime;
}
