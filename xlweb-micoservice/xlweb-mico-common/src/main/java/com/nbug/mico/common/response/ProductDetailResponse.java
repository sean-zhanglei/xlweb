package com.nbug.mico.common.response;

import com.nbug.mico.common.model.product.StoreProduct;
import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 商品详情
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品详情H5")
public class ProductDetailResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "产品属性")
    private List<StoreProductAttr> productAttr;

    @Schema(description = "商品属性详情")
    private HashMap<String, Object> productValue;

    @Schema(description = "返佣金额区间")
    private String priceName;

    @Schema(description = "为移动端特定参数 所有参与的活动")
    private List<ProductActivityItemResponse> activityAllH5;

    @Schema(description = "商品信息")
    private StoreProduct productInfo;

    @Schema(description = "收藏标识")
    private Boolean userCollect;
}
