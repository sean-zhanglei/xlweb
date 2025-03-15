package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用户区域数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户区域数据对象")
public class UserAreaDataResponse implements Serializable {

    private static final long serialVersionUID = -6332062115310922579L;

    @Schema(description = "区域（省份+其他）")
    private String area;

    @Schema(description = "累计用户数量")
    private Integer userNum;

    @Schema(description = "成交用户数量")
    private Integer payUserNum;

    @Schema(description = "支付金额")
    private BigDecimal payAmount;

}
