package com.nbug.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 优惠卷模板搜索

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_coupon")
@ApiModel(value="StoreCouponSearchRequest对象", description="优惠券模板搜索")
public class StoreCouponSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "优惠券名称")
    private String name;

    @ApiModelProperty(value = "优惠券类型 1 手动领取, 2 新人券, 3 赠送券")
    private Integer type;

    @ApiModelProperty(value = "使用类型 1 全场通用, 2 商品券, 3 品类券")
    private Integer useType;

    @ApiModelProperty(value = "状态（0：关闭，1：开启）")
    private Boolean status;

}
