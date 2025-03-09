package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 商品所参与的活动类型

 */
@Data
public class ProductActivityItemResponse {

    @Schema(description = "参与活动id")
    private Integer id;

    @Schema(description = "活动结束时间")
    private Integer time;

    @Schema(description = "活动参与类型:1=秒杀，2=砍价，3=拼团")
    private String type;
}
