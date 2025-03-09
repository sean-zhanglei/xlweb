package com.nbug.mico.common.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nbug.mico.common.utils.date.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * StoreSeckillMsnsgerResponse

 */
@Data
public class StoreSeckillManagerResponse {
    private static final long serialVersionUID=1L;

    // 自定义statusName值
    // 开启 =     status = 1 && 当前时间小于startTime
    // 关闭 =     status = 0
    // 进行中 =    status = 1 && 当前时间大于startTime 小于 stopTime
    // 已结束 =    status = 1 && 当前时间大于结束时间
    // 秒杀状态【仅仅前端用】 killStatus 1=即将开始 0=关闭 2=进行中 -1=已结束
    public String getStatusName() {
        String _statusName = null;
        int currentHour = DateUtil.getCurrentHour();
        if(status == 1 && currentHour < startTime){
            _statusName = "即将开始";
        }
        else if(status == 0) {
            _statusName = "关闭";
        }
        else if(status == 1 && currentHour < endTime) {
            _statusName = "进行中";
        }
        else if(status == 1 && currentHour >= endTime) {
            _statusName = "已结束";
        }
        return _statusName;
    }

    public Integer getKillStatus() {
        Integer _killStatus = null;
        if(status == 1 && DateUtil.getCurrentHour() < startTime){
            _killStatus = 1;
        }
        else if(status == 0) {
            _killStatus = 0;
        }
        else if(status == 1 && DateUtil.getCurrentHour() >= startTime
                && DateUtil.getCurrentHour() < endTime) {
            _killStatus = 2;
        }
        else if(status == 1 && DateUtil.getCurrentHour() >= endTime) {
            _killStatus = -1;
        }
        return _killStatus;
    }

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "秒杀名称")
    private String name;

    @Schema(description = "秒杀开始时间段")
    private Integer startTime;

    @Schema(description = "秒杀结束时间段")
    private Integer endTime;

    private String time;

    @Schema(description = "主图")
    private String img;

    @Schema(description = "轮播图")
    private String silderImgs;

    @Schema(description = "秒杀状态，前端用")
    private Integer killStatus;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态 0=关闭 1=开启")
    private Integer status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "0未删除1已删除")
    private Boolean isDel;

    @Schema(description = "秒杀状态String 未开始/进行中/活动已结束")
    private String statusName;
}
