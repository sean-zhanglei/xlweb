package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 推广人信息

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="推广人信息")
public class UserSpreadPeopleItemResponse implements Serializable {

    private static final long serialVersionUID=1L;


    @Schema(description = "用户编号")
    private Integer uid;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "添加时间")
    private String time;

    @Schema(description = "推广人数")
    private Integer childCount;

    @Schema(description = "订单数量")
    private Integer orderCount;

    @Schema(description = "订单金额")
    private BigDecimal numberCount;

}
