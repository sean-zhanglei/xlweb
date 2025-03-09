package com.nbug.mico.common.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 用户佣金记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_brokerage_record")
@Schema(description="用户佣金记录表")
public class UserBrokerageRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "关联id（orderNo,提现id）")
    private String linkId;

    @Schema(description = "关联类型（order,extract，yue）")
    private String linkType;

    @Schema(description = "类型：1-增加，2-扣减（提现）")
    private Integer type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "金额")
    private BigDecimal price;

    @Schema(description = "剩余")
    private BigDecimal balance;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款），5-提现申请")
    private Integer status;

    @Schema(description = "冻结期时间（天）")
    private Integer frozenTime;

    @Schema(description = "解冻时间")
    private Long thawTime;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "分销等级")
    private Integer brokerageLevel;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String userName;
}
