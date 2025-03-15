package com.nbug.mico.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品评论查询对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="商品评论查询对象")
public class StoreProductReplySearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品名称")
    private String productSearch;

    @Schema(description = "0未回复1已回复")
    private Boolean isReply;

    @Schema(description = "用户名称(支持模糊搜索)")
    private String nickname;

    @Schema(description = "时间区间")
    private String dateLimit;
}
