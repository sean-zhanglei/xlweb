package com.nbug.mico.common.model.article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章管理表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_article")
@Schema(description="文章管理表")
public class Article implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "文章管理ID")
    @TableId(value = "id", type = IdType.AUTO)
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

    @Schema(description = "文章分享标题")
    private String shareTitle;

    @Schema(description = "文章分享简介")
    private String shareSynopsis;

    @Schema(description = "浏览次数")
    private String visit;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "原文链接")
    private String url;

    @Schema(description = "微信素材id")
    private String mediaId;

    @Schema(description = "状态")
    private Boolean status;

    @Schema(description = "是否隐藏")
    private Boolean hide;

    @Schema(description = "管理员id")
    private Integer adminId;

    @Schema(description = "商户id")
    private Integer merId;

    @Schema(description = "商品关联id")
    private Integer productId;

    @Schema(description = "是否热门(小程序)")
    private Boolean isHot;

    @Schema(description = "是否轮播图(小程序)")
    private Boolean isBanner;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "创建时间")
    private Date updateTime;


}
