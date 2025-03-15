package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商城商品统计数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商城商品统计数据对象")
public class ShoppingProductDataResponse implements Serializable {

    private static final long serialVersionUID = -2853994865375523003L;

    @Schema(description = "新增商品数量")
    private Integer newProductNum;

    @Schema(description = "新增商品数量环比")
    private String newProductNumRatio;

    @Schema(description = "浏览量")
    private Integer pageView;

    @Schema(description = "浏览量环比")
    private String pageViewRatio;

    @Schema(description = "收藏量")
    private Integer collectNum;

    @Schema(description = "收藏量环比")
    private String collectNumRatio;

    @Schema(description = "加购件数")
    private Integer addCartNum;

    @Schema(description = "加购件数环比")
    private String addCartNumRatio;

    @Schema(description = "交易总件数")
    private Integer orderProductNum;

    @Schema(description = "交易总件数环比")
    private String orderProductNumRatio;

    @Schema(description = "交易成功件数")
    private Integer orderSuccessProductNum;

    @Schema(description = "交易成功件数环比")
    private String orderSuccessProductNumRatio;

}
