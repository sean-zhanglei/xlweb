package com.nbug.mico.common.response;

import com.nbug.mico.common.request.StoreCouponRequest;
import com.nbug.mico.common.model.category.Category;
import com.nbug.mico.common.model.product.StoreProduct;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠券记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="优惠券详情")
public class StoreCouponInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    public StoreCouponInfoResponse(StoreCouponRequest coupon, List<StoreProduct> product, List<Category> category) {
        this.coupon = coupon;
        this.product = product;
        this.category = category;
    }

    @Schema(description = "优惠券发布id")
    private StoreCouponRequest coupon;

    @Schema(description = "商品信息")
    private List<StoreProduct> product;

    @Schema(description = "分类信息")
    private List<Category> category;

}
