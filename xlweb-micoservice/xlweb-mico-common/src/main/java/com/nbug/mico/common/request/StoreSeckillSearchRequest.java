package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 秒杀搜索参数

 */
@Data
public class StoreSeckillSearchRequest {

    @Schema(description = "搜索关键字 商品id或者名称")
    private String keywords;

    @Schema(description = "秒杀时段")
    private Integer timeId;

    @Schema(description = "是否显示关键字 0/1")
    private Integer status;
}

