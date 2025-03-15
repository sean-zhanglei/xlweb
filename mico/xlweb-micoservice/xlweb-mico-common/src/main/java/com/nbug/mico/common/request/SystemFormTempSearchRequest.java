package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 表单模板

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_form_temp")
@Schema( description="表单模板")
public class SystemFormTempSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "搜索关键字")
    private String keywords;

}
