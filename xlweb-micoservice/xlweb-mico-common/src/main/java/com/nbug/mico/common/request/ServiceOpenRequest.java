package com.nbug.mico.common.request;

import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * 服务开通请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description = "服务开通请求对象")
public class ServiceOpenRequest {

    private static final long serialVersionUID = 1L;

    @Schema(description = "服务类型:sms,短信;copy,产品复制;expr_query,物流查询;expr_dump,电子面单", required = true)
    @NotBlank(message = "服务类型不能为空")
    @StringContains(limitValues = {"sms","copy","expr_query","expr_dump"}, message = "未知的服务类型")
    private String type;

    @Schema(description = "短信签名，短信开通必填")
    private String sign;

    @Schema(description = "快递公司简称，电子面单开通必填")
    private String com;

    @Schema(description = "快递公司模板Id、电子面单开通必填")
    private String tempId;

    @Schema(description = "快递面单发货人姓名，电子面单开通必填")
    private String toName;

    @Schema(description = "快递面单发货人电话，电子面单开通必填")
    private String toTel;

    @Schema(description = "发货人详细地址，电子面单开通必填")
    private String toAddress;

    @Schema(description = "电子面单打印机编号，电子面单开通必填")
    private String siid;

}
