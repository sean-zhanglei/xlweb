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
 * 用户分组表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_group")
@Schema(description="用户分组表")
public class UserGroupRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户分组名称")
    @NotBlank(message = "请填写分组名称")
    @Length(max = 64, message = "用户分组名称不能超过64个字符")
    private String groupName;

}
