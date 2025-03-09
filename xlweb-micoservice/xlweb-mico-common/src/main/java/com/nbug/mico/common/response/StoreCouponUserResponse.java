package com.nbug.mico.common.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠券记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="优惠券记录表")
public class StoreCouponUserResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "优惠券发布id")
    private Integer couponId;

    @Schema(description = "兑换的项目id")
    private Integer cid;

    @Schema(description = "领取人id")
    private Integer uid;

    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "优惠券的面值")
    private BigDecimal money;

    @Schema(description = "最低消费多少金额可用优惠券")
    private BigDecimal minPrice;

    @Schema(description = "获取方式")
    private String type;

    @Schema(description = "状态（0：未使用，1：已使用, 2:已失效）")
    private int status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "开始使用时间")
    private Date startTime;

    @Schema(description = "过期时间")
    private Date endTime;

    @Schema(description = "使用时间")
    private Date useTime;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "用户头像")
    private Boolean isValid;

    @Schema(description = "使用类型 1 全场通用, 2 商品券, 3 品类券")
    private Integer useType;

    @Schema(description = "主键id 商品id/分类id", required = true)
    private String primaryKey;

    @Schema(description = "有效状态：usable-可用，unusable-已用，overdue-过期，notStart-未开始")
    private String validStr;

    @Schema(description = "开始使用时间字符串")
    private String useStartTimeStr;

    @Schema(description = "过期时间字符串")
    private String useEndTimeStr;
}
