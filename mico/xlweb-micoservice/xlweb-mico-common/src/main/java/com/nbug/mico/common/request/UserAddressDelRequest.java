package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 用户地址表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户地址")
public class UserAddressDelRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户地址id")
    @Min(value = 1, message = "请选择用户地址")
    private Integer id;
}
