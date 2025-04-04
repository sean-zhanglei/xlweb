package com.nbug.mico.common.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 商品点赞和收藏表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="商品点赞和收藏表")
public class UserCollectAllRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "商品ID")
    @JsonProperty("id")
    @Size(min = 1, message = "请选择产品")
    private Integer[] productId;

    @Schema(description = "产品类型|store=普通产品,product_seckill=秒杀产品(默认 普通产品 store)")
    @NotBlank(message = "请选择产品类型")
    private String category;
}
