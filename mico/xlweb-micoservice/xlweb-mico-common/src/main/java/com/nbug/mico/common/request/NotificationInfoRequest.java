package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 系统通知详情请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="系统通知详情请求对象")
public class NotificationInfoRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "通知id")
    @NotNull(message = "通知id不能为空")
    private Integer id;

    @Schema(description = "wechat-公众号模板消息，routine-小程序订阅消息，sms-短信")
    @NotEmpty(message = "详情类型不能为空")
    private String detailType;

}
