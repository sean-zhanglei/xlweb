package com.nbug.mico.common.request;

import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 系统菜单请求对象
 * </p>
 *
 * @author HZW
 * @since 2021-11-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="系统菜单请求对象")
public class SystemMenuRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "菜单ID，新增时不填，修改时必填")
    private Integer id;

    @Schema(description = "父级ID")
    private Integer pid;

    @NotEmpty(message = "菜单名称不能为空")
    @Schema(description = "名称")
    @Length(max = 100, message = "菜单名称不能超过100个字符")
    private String name;

    @Schema(description = "icon")
    private String icon;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "组件路径")
    private String component;

    @NotEmpty(message = "菜单类型不能为空")
    @Schema(description = "类型，M-目录，C-菜单，A-按钮")
    @StringContains(limitValues = {"M","C","A"}, message = "未知的菜单类型")
    private String menuType;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;

    @Schema(description = "显示状态")
    @NotNull(message = "显示状态不能为空")
    private Boolean isShow;


}
