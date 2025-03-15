package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * PC登录返回对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="PC登录返回对象")
public class SystemLoginResponse implements Serializable {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "管理员账号")
    private String account;

    @Schema(description = "管理员昵称")
    private String realName;

    @Schema(description = "token")
    private String Token;

    @Schema(description = "是否接收短信")
    private Boolean isSms;
}
