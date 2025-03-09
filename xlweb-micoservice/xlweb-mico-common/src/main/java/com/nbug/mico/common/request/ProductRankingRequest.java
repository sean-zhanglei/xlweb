package com.nbug.mico.common.request;

import com.nbug.mico.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 商品排行请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="商品排行请求对象")
public class ProductRankingRequest implements Serializable {

    private static final long serialVersionUID = 3362714265772774491L;

    @Schema(description = "排序参数:pageviews-浏览量,collectNum-收藏数,addCartNum-加购数,salesNum-销量,salesAmount-销售额")
    @NotBlank(message = "请选择排序参数")
    private String sortKey;

    @Schema(description = "时间参数")
    @NotBlank(message = "请先选择时间")
    private String dateLimit;

    @Schema(description = "页码", example= Constants.DEFAULT_PAGE + "")
    private int page = Constants.DEFAULT_PAGE;

    @Schema(description = "每页数量", example = Constants.DEFAULT_LIMIT + "")
    private int limit = Constants.DEFAULT_LIMIT;
}
