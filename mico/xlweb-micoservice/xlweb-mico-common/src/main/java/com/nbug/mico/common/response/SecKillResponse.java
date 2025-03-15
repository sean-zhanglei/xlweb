package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 *  秒杀Header时间header响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="秒杀Header时间header响应对象")
public class SecKillResponse {

    public SecKillResponse() {
    }

    public SecKillResponse(Integer id, String slide, String statusName, String time, int status, String timeSwap) {
        this.id = id;
        this.slide = slide;
        this.statusName = statusName;
        this.time = time;
        this.status = status;
        this.timeSwap = timeSwap;
    }

    @Schema(description = "秒杀时段id")
    private Integer id;

    @Schema(description = "秒杀时段轮播图")
    private String slide;

    @Schema(description = "秒杀时段状态名称")
    private String statusName; // 已结束 抢购中 即将开始

    @Schema(description = "秒杀时段状态")
    private int status;

    @Schema(description = "秒杀时段时间信息")
    private String time;

    @Schema(description = "秒杀时段结束时间")
    private String timeSwap;

    @Schema(description = "是否选中")
    private Boolean isCheck = false;
}
