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
 * 用户token表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_token")
@Schema(description="UserToken对象")
public class UserToken implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户 id")
    private Integer uid;

    @Schema(description = "token")
    private String token;

    @Schema(description = "类型， 1公众号， 2小程序, 5AppIos,6AppAndroid,7ios")
    private int type;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "到期事件")
    private Date expiresTime;

    @Schema(description = "登录ip")
    private String loginIp;


}
