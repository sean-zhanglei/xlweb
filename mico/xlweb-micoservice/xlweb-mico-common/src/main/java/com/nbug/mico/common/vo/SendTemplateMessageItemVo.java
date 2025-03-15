package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 微信模板发送数据类

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="微信模板发送数据类")
public class SendTemplateMessageItemVo {
    public SendTemplateMessageItemVo() {}
    public SendTemplateMessageItemVo(String value) {
        this.value = value;
    }

    @Schema(description = "显示的文字内容", required = true)
    private String value;

    @Schema(description = "颜色")
    private String color = "#173177";
}
