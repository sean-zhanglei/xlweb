package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * 组合数据表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_group")
@Schema(description="组合数据表")
public class SystemGroupRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "数据组名称")
    @Length(max = 50, message = "数据组名称长度不能超过50个字符")
    private String name;

    @Schema(description = "简介")
    @Length(max = 256, message = "数据组名称长度不能超过256个字符")
    private String info;

    @Schema(description = "form 表单 id")
    private Integer formId;

}
