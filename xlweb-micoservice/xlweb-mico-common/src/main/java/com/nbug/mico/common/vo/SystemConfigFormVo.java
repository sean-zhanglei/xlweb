package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * SystemConfigFormVo对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="form对象")
public class SystemConfigFormVo implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "表单名称")
    private String formRef;

    @Schema(description = "form对象")
    private String formModel;

    @Schema(description = "大小")
    private String size;

    @Schema(description = "label位置")
    private String labelPosition;

    @Schema(description = "label宽度")
    private String labelWidth;

    @Schema(description = "form规则")
    private String formRules;

    @Schema(description = "")
    private String gutter;

    @Schema(description = "是否禁用")
    private String disabled;

    @Schema(description = "span")
    private String span;

    @Schema(description = "button")
    private String formBtns;

    @Schema(description = "字段值列表")
    private List<String> fields;



}
