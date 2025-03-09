package com.nbug.mico.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 商品属性添加对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="商品属性添加对象")
public class StoreProductAttrAddRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "attrID|新增时不填，修改时必填")
    private Integer id;

    @Schema(description = "属性名", required = true)
    private String attrName;

    @Schema(description = "属性值|逗号分隔", required = true)
    private String attrValues;
}
