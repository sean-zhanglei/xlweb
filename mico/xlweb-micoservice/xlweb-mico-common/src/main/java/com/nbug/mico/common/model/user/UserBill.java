package com.nbug.mico.common.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户账单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_bill")
@Schema(description="用户账单表")
public class UserBill implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户账单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "关联id")
    private String linkId;

    @Schema(description = "0 = 支出 1 = 获得")
    private int pm;

    @Schema(description = "账单标题")
    private String title;

    @Schema(description = "明细种类")
    private String category;

    @Schema(description = "明细类型")
    private String type;

    @Schema(description = "明细数字")
    private BigDecimal number;

    @Schema(description = "剩余")
    private BigDecimal balance;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "0 = 带确定 1 = 有效 -1 = 无效")
    private Integer status;

    @Schema(description = "创建时间")
    private Date updateTime;

    @JsonProperty(value = "add_time")
    @Schema(description = "创建时间")
    private Date createTime;

}
