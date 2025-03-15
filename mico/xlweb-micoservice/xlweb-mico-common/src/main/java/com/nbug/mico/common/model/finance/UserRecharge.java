package com.nbug.mico.common.model.finance;

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
 * 用户充值表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_recharge")
@Schema(description="用户充值表")
public class UserRecharge implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "充值用户UID")
    private Integer uid;

    @Schema(description = "订单号")
    private String orderId;

    @Schema(description = "充值金额")
    private BigDecimal price;

    @Schema(description = "购买赠送金额")
    private BigDecimal givePrice;

    @Schema(description = "充值类型")
    private String rechargeType;

    @Schema(description = "是否充值")
    private Boolean paid;

    @Schema(description = "充值支付时间")
    private Date payTime;

    @Schema(description = "充值时间")
    private Date createTime;

    @Schema(description = "退款金额")
    private BigDecimal refundPrice;


}
