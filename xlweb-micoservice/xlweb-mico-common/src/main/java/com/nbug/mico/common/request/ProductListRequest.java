package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品列表请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="商品列表请求对象")
public class ProductListRequest implements Serializable {

    private static final long serialVersionUID = 3481659942630712958L;

    @Schema(description = "搜索关键字")
    private String keyword;

    @Schema(description = "分类id")
    private Integer cid;

}
