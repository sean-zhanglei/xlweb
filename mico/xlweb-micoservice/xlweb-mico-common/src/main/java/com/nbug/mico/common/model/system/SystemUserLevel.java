package com.nbug.mico.common.model.system;

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
 * 用户等级表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_user_level")
@Schema(description="用户等级表")
public class SystemUserLevel implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "会员名称")
    private String name;

    @Schema(description = "购买金额|经验达到")
    private Integer experience;

    @Schema(description = "是否显示 1=显示,0=隐藏")
    private Boolean isShow;

    @Schema(description = "会员等级")
    private Integer grade;

    @Schema(description = "享受折扣")
    private Integer discount;

    @Schema(description = "会员图标")
    private String icon;

    @Schema(description = "是否删除.1=删除,0=未删除")
    private Boolean isDel;

    @Schema(description = "创建时间")
    private Date updateTime;

    @Schema(description = "创建时间")
    private Date createTime;
}
