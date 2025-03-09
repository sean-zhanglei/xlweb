package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 物流轨迹对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "物流轨迹对象")
public class LogisticsTrackVo {

    private static final long serialVersionUID=1L;

    @Schema(description = "变动时间")
    private String time;

    private String ftime;

    @Schema(description = "变动详情")
    private String status;
}
