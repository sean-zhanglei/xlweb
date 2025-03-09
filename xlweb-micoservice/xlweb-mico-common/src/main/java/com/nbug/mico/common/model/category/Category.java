package com.nbug.mico.common.model.category;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 分类表
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_category")
@Schema(description="分类表")
public class Category implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "路径")
    private String path;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "类型ID | 类型，1 产品分类，2 附件分类，3 文章分类， 4 设置分类， 5 菜单分类， 6 配置分类， 7 秒杀配置 ")
    private Integer type;

    @Schema(description = "地址")
    private String url;

    @Schema(description = "扩展字段")
    private String extra;

    @Schema(description = "状态,1正常，0失效")
    private Boolean status;

    @Schema(description = "排序")
    private Integer sort;


}
