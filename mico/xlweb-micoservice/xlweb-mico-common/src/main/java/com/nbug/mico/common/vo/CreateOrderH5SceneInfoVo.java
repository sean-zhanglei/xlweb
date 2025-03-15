package com.nbug.mico.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 上报支付的场景信息

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="上报支付的场景信息")
public class CreateOrderH5SceneInfoVo {
    public CreateOrderH5SceneInfoVo() {
    }

    public CreateOrderH5SceneInfoVo(CreateOrderH5SceneInfoDetailVo h5Info) {
        this.h5_info = h5Info;
    }

    @Schema(description = "h5_info", required = true)
    @JsonProperty(value = "h5_info")
    private CreateOrderH5SceneInfoDetailVo h5_info;
}
