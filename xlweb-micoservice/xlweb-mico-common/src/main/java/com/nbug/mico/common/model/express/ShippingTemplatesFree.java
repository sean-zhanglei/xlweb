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
 * 免费运费模版
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_shipping_templates_free")
@Schema(description="ShippingTemplatesFree对象")
public class ShippingTemplatesFree implements Serializable {

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

    @Schema(description = "包邮件数")
    private BigDecimal number;

    @Schema(description = "包邮金额")
    private BigDecimal price;

    @Schema(description = "计费方式")
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
