package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户渠道数据对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户渠道数据对象")
public class UserChannelDataResponse implements Serializable {

    private static final long serialVersionUID = -6004724917253583732L;

    @Schema(description = "性别,h5,ios,routine,wechat")
    private String channel;

    @Schema(description = "数量")
    private Integer num;
}
