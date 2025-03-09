package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 整体保存表单数据

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="整体保存表单数据")
public class SystemFormCheckRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "表单名称", required = true)
    @Min(value = 0, message = "请选择表单")
    private Integer id;

    @Schema(description = "排序", required = true)
    private Integer sort;

    @Schema(description = "状态（1：开启；0：关闭；）")
    private Boolean status;

    @Schema(description = "字段值列表", required = true)
    @NotEmpty(message = "fields 至少要有一组数据")
    @Size(min = 1, message = "fields 至少要有一组数据")
    private List<SystemFormItemCheckRequest> fields;

}
