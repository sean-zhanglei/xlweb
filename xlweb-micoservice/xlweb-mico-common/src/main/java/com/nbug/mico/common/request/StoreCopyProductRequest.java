package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 复制商品请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="复制商品请求对象")
public class StoreCopyProductRequest {

    @Schema(description = "复制商品地址")
    @NotBlank(message = "复制商品地址 不能为空")
    private String url;

}
