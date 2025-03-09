package com.nbug.mico.common.model.combination;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拼团表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_pink")
@Schema(description="拼团表")
public class StorePink implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团ID")
    @TableId(value = "id", type = IdType.AUTO)
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


}
