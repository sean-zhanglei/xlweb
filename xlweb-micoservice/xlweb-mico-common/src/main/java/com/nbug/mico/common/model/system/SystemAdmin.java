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
 * 后台管理员表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_admin")
@Schema(description="后台管理员表")
public class SystemAdmin implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "后台管理员表ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "后台管理员账号")
    private String account;

    @Schema(description = "后台管理员密码")
    private String pwd;

    @Schema(description = "后台管理员姓名")
    private String realName;

    @Schema(description = "后台管理员权限(menus_id)")
    private String roles;

    @Schema(description = "后台管理员最后一次登录ip")
    private String lastIp;

    @Schema(description = "后台管理员最后一次登录时间")
    private Date updateTime;

    @Schema(description = "后台管理员添加时间")
    private Date createTime;

    @Schema(description = "后台管理员级别")
    private Integer level;

    @Schema(description = "后台管理员状态 1有效0无效")
    private Boolean status;

    @Schema(description = "是否删除 1是0否")
    private Boolean isDel;

    @Schema(description = "登录次数")
    private Integer loginCount;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "是否接收短信")
    private Boolean isSms;
}
