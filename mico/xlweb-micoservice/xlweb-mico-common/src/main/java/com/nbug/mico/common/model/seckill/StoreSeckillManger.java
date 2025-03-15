package com.nbug.mico.common.model.seckill;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品秒杀管理表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_seckill_manger")
@Schema(description="商品秒杀管理表")
public class StoreSeckillManger implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "秒杀名称")
    private String name;

    @Schema(description = "秒杀开始时间段")
    private Integer startTime;

    @Schema(description = "秒杀结束时间段")
    private Integer endTime;

    @Schema(description = "主图")
    private String img;

    @Schema(description = "轮播图")
    private String silderImgs;

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


}
