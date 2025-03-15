package com.nbug.mico.common.response;

import com.nbug.mico.common.model.product.StoreProductAttr;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 拼团商品响应体

 */
@Data
public class CombinationDetailResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团列表")
    private List<StorePinkResponse> pinkList;

    @Schema(description = "拼团成功列表")
    private List<StorePinkResponse> pinkOkList;

    @Schema(description = "拼团完成的商品总件数")
    private Integer pinkOkSum;

    @Schema(description = "拼团商品信息")
    private CombinationDetailH5Response storeCombination;

    @Schema(description = "商品规格")
    private List<StoreProductAttr> productAttr;

    @Schema(description = "商品规格值")
    private HashMap<String,Object> productValue;

    @Schema(description = "收藏标识")
    private Boolean userCollect;

    @Schema(description = "主商品状态:normal-正常，sellOut-售罄，soldOut-下架,delete-删除")
    private String masterStatus;
}
