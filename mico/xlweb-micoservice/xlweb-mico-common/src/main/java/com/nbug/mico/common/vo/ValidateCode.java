package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 验证码类

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(name="ValidateCode对象", description="验证码类")
public class ValidateCode implements Serializable {

    public ValidateCode(String key, String code) {
        this.key = key;
        this.code = code;
    }

    @Schema(name = "key", required = true)
    private String key;

    @Schema(name = "code", required = true)
    private String code;

}
