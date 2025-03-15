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
 * 签到记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_sign")
@Schema(description="签到记录表")
public class UserSign implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "签到说明")
    private String title;

    @Schema(description = "获得积分")
    private Integer number;

    @Schema(description = "剩余积分")
    private Integer balance;

    @Schema(description = "类型，1积分，2经验")
    private Integer type;

    @Schema(description = "签到日期")
    private Date createDay;

    @Schema(description = "添加时间")
    private Date createTime;


}
