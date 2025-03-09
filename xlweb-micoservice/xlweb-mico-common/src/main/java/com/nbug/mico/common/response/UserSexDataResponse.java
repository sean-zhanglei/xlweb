package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户性别数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户性别数据对象")
public class UserSexDataResponse implements Serializable {

    private static final long serialVersionUID = -6004724917253583732L;

    @Schema(description = "性别,0未知，1男，2女，3保密")
    private Integer sex;

    @Schema(description = "数量")
    private Integer num;
}
