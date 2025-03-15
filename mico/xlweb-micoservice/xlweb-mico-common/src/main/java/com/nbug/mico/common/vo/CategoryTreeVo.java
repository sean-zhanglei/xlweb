package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类表
 
 */
@Data
public class CategoryTreeVo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "类型，类型，1 产品分类，2 附件分类，3 文章分类， 4 设置分类， 5 菜单分类， 6 配置分类， 7 秒杀配置")
    private Integer type;

    @Schema(description = "地址")
    private String url;

    @Schema(description = "扩展字段")
    private String extra;

    @Schema(description = "状态, 0正常，1失效")
    private Boolean status;

    @Schema(description = "排序")
    private Integer sort;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) //属性为 空（""）[] 或者为 NULL 都不序列化
    private List<CategoryTreeVo> child = new ArrayList<>();
}
