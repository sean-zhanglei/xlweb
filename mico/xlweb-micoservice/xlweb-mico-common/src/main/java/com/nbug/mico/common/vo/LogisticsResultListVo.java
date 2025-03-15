package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  快递轨迹
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="快递轨迹")
public class LogisticsResultListVo {

    @Schema(description = "时间")
    private String time;

    @Schema(description = "日志")
    private String status;
}
