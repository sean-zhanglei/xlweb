package com.nbug.mico.common.model.order;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单操作记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_order_status")
@Schema(description="订单操作记录表")
public class StoreOrderStatus implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单id")
    private Integer oid;

    @Schema(description = "操作类型")
    private String changeType;

    @Schema(description = "操作备注")
    private String changeMessage;

    @Schema(description = "操作时间")
    private Date createTime;


}
