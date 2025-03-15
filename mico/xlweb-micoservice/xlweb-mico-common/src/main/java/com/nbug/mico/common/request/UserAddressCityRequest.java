package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户地址详细对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="用户地址详细对象")
public class UserAddressCityRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "收货人所在省", required = true)
    @NotBlank(message = "收货人所在省不能为空")
    private String province;

    @Schema(description = "收货人所在市", required = true)
    @NotBlank(message = "收货人所在市不能为空")
    private String city;

    @Schema(description = "城市id")
    private Integer cityId = 0;

    @Schema(description = "收货人所在区", required = true)
    @NotBlank(message = "收货人所在区不能为空")
    private String district;

}
