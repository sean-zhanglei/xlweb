package com.nbug.mico.common.model.user;

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
 * 用户经验记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_experience_record")
@Schema(description="用户经验记录表")
public class UserExperienceRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "记录id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "关联id-orderNo,(sign,system默认为0）")
    private String linkId;

    @Schema(description = "关联类型（order,sign,system）")
    private String linkType;

    @Schema(description = "类型：1-增加，2-扣减")
    private Integer type;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "经验")
    private Integer experience;

    @Schema(description = "剩余")
    private Integer balance;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态：1-成功（保留字段）")
    private Integer status;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;


}
