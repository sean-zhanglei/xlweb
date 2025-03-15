package com.nbug.mico.common.model.bargain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 砍价用户帮助表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_bargain_user_help")
@Schema(description="砍价用户帮助表")
public class StoreBargainUserHelp implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "砍价用户帮助表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "帮助的用户id")
    private Integer uid;

    @Schema(description = "砍价商品ID")
    private Integer bargainId;

    @Schema(description = "用户参与砍价表id")
    private Integer bargainUserId;

    @Schema(description = "帮助砍价多少金额")
    private BigDecimal price;

    @Schema(description = "添加时间")
    private Long addTime;

    @Schema(description = "用户昵称")
    @TableField(exist = false)
    private String nickname;

    @Schema(description = "用户头像")
    @TableField(exist = false)
    private String avatar;

    @Schema(description = "添加时间(前端用)")
    @TableField(exist = false)
    private String addTimeStr;
}
