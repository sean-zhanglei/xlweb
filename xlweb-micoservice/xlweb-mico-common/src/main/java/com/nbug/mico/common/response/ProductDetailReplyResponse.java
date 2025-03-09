package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品详情评论响应对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品详情评论响应对象")
public class ProductDetailReplyResponse implements Serializable {

    private static final long serialVersionUID = 8822745472328151094L;

    @Schema(description = "评论总数")
    private Integer sumCount;

    @Schema(description = "好评率")
    private String replyChance;

    @Schema(description = "最后一条评论信息")
    private ProductReplyResponse productReply;
}
