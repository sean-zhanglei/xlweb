package com.nbug.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 物流查询结果对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="LogisticsQueryVo对象", description = "物流查询结果对象")
public class LogisticsQueryVo {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "是否签收")
    private String ischeck;

    @ApiModelProperty(value = "物流状态：0：快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收 ")
    private String status;

    @ApiModelProperty(value = "物流公司")
    private String com;

    @ApiModelProperty(value = "快递单号")
    private String num;

    @ApiModelProperty(value = "物流详情")
    private List<LogisticsTrackVo> content;

}
