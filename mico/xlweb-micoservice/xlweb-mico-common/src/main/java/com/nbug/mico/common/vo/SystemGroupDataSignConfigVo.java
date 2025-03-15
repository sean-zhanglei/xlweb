package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 签到记录

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="签到记录")
public class SystemGroupDataSignConfigVo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id")
    private Integer id;

    @Schema(description = "显示文字")
    private String title;

    @Schema(description = "第几天")
    private Integer day;


    @Schema(description = "积分")
    private Integer integral;

    @Schema(description = "经验")
    private Integer experience;




}
