package com.nbug.mico.common.model.coupon;

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
 * 优惠券表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_coupon")
@Schema(description="优惠券表")
public class StoreCoupon implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "优惠券表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "优惠券名称")
    private String name;

    @Schema(description = "兑换的优惠券面值")
    private BigDecimal money;

    @Schema(description = "是否限量, 默认0 不限量， 1限量")
    private Boolean isLimited;

    @Schema(description = "发放总数")
    private Integer total;

    @Schema(description = "剩余数量")
    private Integer lastTotal;

    @Schema(description = "使用类型 1 全场通用, 2 商品券, 3 品类券")
    private Integer useType;

    @Schema(description = "主键id 商品id/分类id", required = true)
    private String primaryKey;

    @Schema(description = "最低消费，0代表不限制")
    private BigDecimal minPrice;

    @Schema(description = "可领取开始时间")
    private Date receiveStartTime;

    @Schema(description = "可领取结束时间")
    private Date receiveEndTime;

    @Schema(description = "是否固定使用时间, 默认0 否， 1是")
    private Boolean isFixedTime;

    @Schema(description = "可使用时间范围 开始时间")
    private Date useStartTime;

    @Schema(description = "可使用时间范围 结束时间")
    private Date useEndTime;

    @Schema(description = "天数")
    private Integer day;

    @Schema(description = "优惠券类型 1 手动领取, 2 新人券, 3 赠送券")
    private Integer type;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态（0：关闭，1：开启）")
    private Boolean status;

    @Schema(description = "是否删除 状态（0：否，1：是）")
    private Boolean isDel;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "是否领取")
    @TableField(exist = false)
    private Boolean isGet = false;
}
