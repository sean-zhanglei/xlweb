package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 *  模版Request

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_shipping_templates")
@Schema( description="模板")
public class ShippingTemplatesRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "模板名称", required = true)
    @NotBlank(message = "模板名称必须填写")
    @Length(max = 200, message = "模板名称不能超过200个字符")
    private String name;

    @Schema(description = "计费方式 1(按件数), 2(按重量)，3(按体积)", example = "1", required = true)
    @NotNull(message = "计费方式必须选择")
    @Range(min = 1, max = 3, message = "计费方式选择区间 1(按件数), 2(按重量)，3(按体积)")
    private Integer type;

    @Schema(description = "配送区域及运费", required = true)
    private List<ShippingTemplatesRegionRequest> shippingTemplatesRegionRequestList;

    @Schema(description = "指定包邮", example = "1", required = true)
    @NotNull(message = "指定包邮必须选择")
    private Boolean appoint;

    @Schema(description = "指定包邮设置", required = true)
    private List<ShippingTemplatesFreeRequest> shippingTemplatesFreeRequestList;

    @Schema(description = "排序", example = "0")
    @NotNull(message = "排序数字必须填写")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;

}
