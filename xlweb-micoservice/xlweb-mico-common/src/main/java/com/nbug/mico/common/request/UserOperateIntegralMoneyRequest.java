package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 资金操作

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="资金操作")
public class UserOperateIntegralMoneyRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "uid")
    @NotNull
    @Min(value = 1, message = "请输入正确的uid")
    private Integer uid;

    @Schema(description = "积分类型， 1 = 增加， 2 = 减少")
    @NotNull
    @Range(min = 1, max = 2, message = "请选择正确的类型， 【1 = 增加， 2 = 减少】")
    private Integer integralType;

    @Schema(description = "积分")
    @Min(value = 0)
    @Max(value = 999999)
    private Integer integralValue;

    @Schema(description = "余额类型， 1 = 增加， 2 = 减少")
    @NotNull
    @Range(min = 1, max = 2, message = "请选择正确的类型， 【1 = 增加， 2 = 减少】")
    private Integer moneyType;

    @Schema(description = "余额")
    @DecimalMin(value = "0.00")
    @DecimalMax(value = "999999.99")
    private BigDecimal moneyValue;

}
