package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 优惠卷表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_coupon")
@Schema( description="优惠券表")
public class StoreCouponRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "优惠券名称", required = true)
    @NotBlank(message = "请填写优惠券名称")
    @Length(max = 64, message = "优惠券名称长度不能超过64个字符")
    private String name;

    @Schema(description = "兑换的优惠券面值", required = true)
    @NotNull(message = "请填写优惠券兑换的优惠券面值")
    @DecimalMax(value = "99999.99", message = "优惠券面值不能大于99999.99")
    private BigDecimal money;

    @Schema(description = "是否限量, 默认0 否， 1是", required = true)
    @NotNull(message = "请设置是否限量")
    private Boolean isLimited;

    @Schema(description = "发放总数")
    private Integer total;

    @Schema(description = "使用类型 1 全场通用, 2 商品券, 3 品类券")
    @Range(min = 1, max = 3, message = "请选择优惠券使用类型")
    private Integer useType;

    @Schema(description = "主键id 商品id/分类id", required = true)
    private String primaryKey;

    @Schema(description = "最低消费，0代表不限制", required = true)
    private BigDecimal minPrice;

    @Schema(description = "是否固定领取时间， 默认0 否， 1是", required = true)
    @NotNull(message = "请选择领取是否限时")
    private Boolean isForever;

    @Schema(description = "可领取开始时间")
    private Date receiveStartTime;

    @Schema(description = "可领取结束时间")
    private Date receiveEndTime;

    @Schema(description = "请设置是否固定使用时间, 默认0 否， 1是", required = true)
    @NotNull(message = "请设置是否固定使用时间")
    private Boolean isFixedTime;

    @Schema(description = "可使用时间范围 开始时间")
    private Date useStartTime;

    @Schema(description = "可使用时间范围 结束时间")
    private Date useEndTime;

    @Schema(description = "天数")
    @Max(value = 999, message = "天数不能超过999天")
    private Integer day;

    @Schema(description = "优惠券类型 1 手动领取, 2 新人券, 3 赠送券")
    @Range(min = 1, max = 3, message = "请选择优惠券领取方式")
    private Integer type;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序不能小于0")
    private Integer sort;

    @Schema(description = "状态（0：关闭，1：开启）")
    @NotNull(message = "优惠券状态不能为空")
    private Boolean status;
}
