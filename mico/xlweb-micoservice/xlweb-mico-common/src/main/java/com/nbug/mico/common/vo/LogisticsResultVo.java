package com.nbug.mico.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *  快递轨迹
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="快递接口返回数据")
public class LogisticsResultVo {

    @Schema(description = "快递单号")
    private String number;

    @Schema(description = "快递简写")
    private String type;

    @Schema(description = "快递运送轨迹")
    private List<LogisticsResultListVo> list;

    @Schema(description = "快递收件(揽件)1.在途中 2.正在派件 3.已签收 4.派送失败 5.疑难件 6.退件签收 */")
    @JsonProperty("deliverystatus")
    private String deliveryStatus;

    @Schema(description = "是否签收")
    @JsonProperty("issign")
    private String isSign;

    @Schema(description = "快递公司名称")
    private String expName;

    @Schema(description = "快递公司官网")
    private String expSite;

    @Schema(description = "快递公司电话")
    private String expPhone;

    @Schema(description = "快递员 或 快递站(没有则为空")
    private String courier;

    @Schema(description = "快递员电话 (没有则为空)")
    private String courierPhone;

    @Schema(description = "快递轨迹信息最新时间 ")
    private String updateTime;

    @Schema(description = "发货到收货消耗时长 (截止最新轨迹)")
    private String takeTime;

    @Schema(description = "快递公司LOGO")
    private String logo;

}
