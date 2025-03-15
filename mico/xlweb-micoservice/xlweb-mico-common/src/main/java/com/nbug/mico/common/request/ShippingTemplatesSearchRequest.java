package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *  模板搜索Request
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_shipping_templates")
@Schema( description="模板搜索")
public class ShippingTemplatesSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "模板名称")
    private String keywords;
}
