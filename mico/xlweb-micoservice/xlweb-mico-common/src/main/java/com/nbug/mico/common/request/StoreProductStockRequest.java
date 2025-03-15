package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 商品库存

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="库存修改")
public class StoreProductStockRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品ID", required = true)
    @NotEmpty(message = "请选择商品")
    private Integer productId;

    private Integer seckillId;

    private Integer bargainId;

    private Integer combinationId;

    @Schema(description = "商品属性ID集合", required = true)
    @NotEmpty(message = "请选择商品属性id集合")
    private Integer attrId;

    @Schema(description = "类型， 增加 add | 减少 diff", required = true)
    @NotBlank(message = "请选择类型")
    private String operationType;

    @Schema(description = "数量", required = true)
    @Min(value = 0, message = "请填写数量")
    private Integer num;

    @Schema(description = "商品类型 0=普通 1=秒杀", required = false)
    private Integer type;

    @Schema(description = "商品SKU信息")
    private String suk;
}
