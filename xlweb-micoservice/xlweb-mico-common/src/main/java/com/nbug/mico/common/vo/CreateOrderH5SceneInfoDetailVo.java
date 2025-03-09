package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 上报支付的场景信息详情

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="上报支付的场景信息详情")
public class CreateOrderH5SceneInfoDetailVo {
    public CreateOrderH5SceneInfoDetailVo() {
    }

    public CreateOrderH5SceneInfoDetailVo(String url, String name) {
        this.wap_url = url;
        this.wap_name = name;
    }

    @Schema(description = "场景类型", required = true)
    private String type = "Wap";

    @Schema(description = "WAP网站URL地址", required = true)
    private String wap_url;

    @Schema(description = "WAP 网站名", required = true)
    private String wap_name;
}
