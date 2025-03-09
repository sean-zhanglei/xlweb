package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 组合数据详情表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_group_data")
@Schema( description="组合数据详情表")
public class SystemGroupDataRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "对应的数据组id")
    private Integer gid;

    @Schema(description = "表单数据")
    private SystemFormCheckRequest form;
}
