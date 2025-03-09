package com.nbug.mico.common.response;

import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 商品秒杀详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品秒杀详情响应对象")
public class StoreSeckillDetailResponse implements Serializable {

    private static final long serialVersionUID = -4101548587444327191L;

    @Schema(description = "产品属性")
    private List<StoreProductAttr> productAttr;

    @Schema(description = "商品属性详情")
    private HashMap<String,Object> productValue;

    @Schema(description = "返佣金额区间")
    private String priceName;

    @Schema(description = "收藏标识")
    private Boolean userCollect;

    @Schema(description = "秒杀商品信息")
    private SecKillDetailH5Response storeSeckill;

    @Schema(description = "主商品状态:normal-正常，sellOut-售罄，soldOut-下架,delete-删除")
    private String masterStatus;
}
