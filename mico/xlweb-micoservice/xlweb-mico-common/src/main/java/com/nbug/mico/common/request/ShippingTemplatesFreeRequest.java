package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 免费模版 Request
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_shipping_templates_free")
@Schema( description="免费")
public class ShippingTemplatesFreeRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "城市ID, 多个逗号分割。 全国 all", required = true, example = "1,2,3,4")
    @NotNull(message = "请选择城市")
    private String cityId;

    @Schema(description = "城市名称描述")
    private String title;

    @Schema(description = "包邮件数", required = true, example = "1")
//    @Min(value = 1, message = "请填写包邮件数")
    @DecimalMin(value = "0.1", message = "包邮不能低于0.1")
    private BigDecimal number;

    @Schema(description = "包邮金额", required = true, example = "0.1")
    @NotNull(message = "请填写包邮金额")
    @DecimalMin(value = "0", message = "包邮金额不能低于0")
    private BigDecimal price;
}
