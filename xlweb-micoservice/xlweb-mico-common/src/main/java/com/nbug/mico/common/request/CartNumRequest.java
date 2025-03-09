package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *  购物车数量请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="购物车数量请求对象")
public class CartNumRequest implements Serializable {

    private static final long serialVersionUID = -1186533756329913311L;

    @Schema(description = "数量类型：total-商品数量，sum-购物数量", required = true)
    @NotNull(message = "数量类型不能为空")
    private String type;

    @Schema(description = "商品类型：true-有效商品，false-无效商品", required = true)
    @NotNull(message = "商品类型不能为空")
    private Boolean numType;

}
