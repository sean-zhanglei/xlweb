package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团响应体

 */
@Data
public class StorePinkResponse {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团ID")
    private Integer id;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "订单id 生成")
    private String orderId;

    @Schema(description = "订单id  数据库")
    private Integer orderIdKey;

    @Schema(description = "购买商品个数")
    private Integer totalNum;

    @Schema(description = "购买总金额")
    private BigDecimal totalPrice;

    @Schema(description = "拼团商品id")
    private Integer cid;

    @Schema(description = "商品id")
    private Integer pid;

    @Schema(description = "拼图总人数")
    private Integer people;

    @Schema(description = "拼团商品单价")
    private BigDecimal price;

    @Schema(description = "开始时间")
    private Long addTime;

    @Schema(description = "结束时间")
    private Long stopTime;

    @Schema(description = "团长id 0为团长")
    private Integer kId;

    @Schema(description = "是否发送模板消息0未发送1已发送")
    private Boolean isTpl;

    @Schema(description = "是否退款 0未退款 1已退款")
    private Boolean isRefund;

    @Schema(description = "状态1进行中2已完成3未完成")
    private Integer status;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "是否虚拟拼团")
    private Boolean is_virtual;

    @Schema(description = "几人参团")
    private Integer countPeople;

    @Schema(description = "还剩几人成团")
    private Integer count;
}
