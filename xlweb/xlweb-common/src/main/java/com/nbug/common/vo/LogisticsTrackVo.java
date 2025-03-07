package com.nbug.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 物流轨迹对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LogisticsTrackVo对象", description = "物流轨迹对象")
public class LogisticsTrackVo {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "变动时间")
    private String time;

    private String ftime;

    @ApiModelProperty(value = "变动详情")
    private String status;
}
