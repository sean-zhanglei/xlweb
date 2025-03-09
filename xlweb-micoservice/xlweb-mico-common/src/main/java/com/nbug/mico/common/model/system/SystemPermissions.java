package com.nbug.mico.common.model.system;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author HZW
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
//@TableName("eb_system_permissions")
@Schema(description="系统权限表")
public class SystemPermissions implements Serializable {

    private static final long serialVersionUID=1L;

//    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "id")
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限地址")
    private String path;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "是否删除")
    private Boolean isDelte;


}
