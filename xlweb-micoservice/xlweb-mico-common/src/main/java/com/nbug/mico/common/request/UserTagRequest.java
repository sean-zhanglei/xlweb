package com.nbug.mico.common.request;

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
@Schema( description="会员标签")
public class UserTagRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "标签名称")
    @NotBlank(message = "请填写标签名称")
    @Length(max = 50, message = "标签名称不能超过50个字符")
    private String name;


}
