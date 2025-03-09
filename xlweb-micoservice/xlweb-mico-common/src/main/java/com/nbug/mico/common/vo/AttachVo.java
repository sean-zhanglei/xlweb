package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 支付附加对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="支付附加对象")
public class AttachVo {

    public AttachVo() {
    }

    public AttachVo(String type, Integer userId) {
        this.type = type;
        this.userId = userId;
    }

    @Schema(description = "业务类型， 订单 = order， 充值 = recharge", required = true)
    private String type = "order";

    @Schema(description = "用户id", required = true)
    private Integer userId;

}
