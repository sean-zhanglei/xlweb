package com.nbug.mico.common.model.express;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  运费模版区域
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_shipping_templates_region")
@Schema(description="ShippingTemplatesRegion对象")
public class ShippingTemplatesRegion implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "模板ID")
    private Integer tempId;

    @Schema(description = "城市ID")
    private Integer cityId;

    @Schema(description = "描述")
    private String title;

    @Schema(description = "首件")
    private BigDecimal first;

    @Schema(description = "首件运费")
    private BigDecimal firstPrice;

    @Schema(description = "续件")
    private BigDecimal renewal;

    @Schema(description = "续件运费")
    private BigDecimal renewalPrice;

    @Schema(description = "计费方式 1按件数 2按重量 3按体积")
    private Integer type;

    @Schema(description = "分组唯一值")
    private String uniqid;

    @Schema(description = "是否无效")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
