package com.nbug.mico.common.request;

import com.nbug.mico.common.constants.RegularConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 后台管理员修改对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="后台管理员修改对象")
public class SystemAdminUpdateRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "后台管理员表ID")
    @NotNull(message = "管理员id不能为空")
    private Integer id;

    @Schema(description = "后台管理员账号", required = true)
    @NotNull(message = "后台管理员账号不能为空")
    @Length(max = 32, message = "账号长度不能超过32个字符")
    private String account;

    @Schema(description = "后台管理员密码", required = true)
    @NotNull(message = "管理员密码不能为空")
    @Length(max = 32, message = "密码长度不能超过32个字符")
    private String pwd;

    @Schema(description = "后台管理员姓名", required = true)
    @NotNull(message = "管理姓名不能为空")
    @Length(max = 16, message = "姓名长度不能超过16个字符")
    private String realName;

    @Schema(description = "后台管理员权限(menus_id)")
    @NotBlank(message = "后台管理员权限不能为空")
    @Length(max = 128, message = "角色组合长度不能超过128个字符")
    private String roles;

    @Schema(description = "后台管理员状态 1有效0无效")
    @NotNull(message = "status 字段不能为空")
    @Range(min = 0, max = 1, message = "未知的状态")
    private Boolean status;

    @Schema(description = "手机号码")
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "请填写正确的手机号")
    private String phone;
}
