package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 表单模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_form_temp")
@Schema( description="表单模板")
public class SystemFormTempRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "表单名称", required = true)
    @NotBlank(message = "请填写表单名称")
    @Length(max = 500, message = "表单名称长度不能超过500个字符")
    private String name;

    @Schema(description = "表单简介", required = true)
    @NotBlank(message = "请填写表单简介")
    @Length(max = 500, message = "表单简介长度不能超过500个字符")
    private String info;

    @Schema(description = "表单内容", required = true)
    @NotBlank(message = "请填写表单内容")
    private String content;

}
