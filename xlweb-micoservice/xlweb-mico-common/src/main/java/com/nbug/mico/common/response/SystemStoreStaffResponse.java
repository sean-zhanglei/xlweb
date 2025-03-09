package com.nbug.mico.common.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nbug.mico.common.model.system.SystemStore;
import com.nbug.mico.common.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 门店店员表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_store_staff")
@Schema(description="门店店员表")
public class SystemStoreStaffResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "微信用户id")
    private Integer uid;

    @Schema(description = "店员头像")
    private String avatar;

    @Schema(description = "用户信息")
    private User user;

    @Schema(description = "门店id")
    private Integer storeId;

    @Schema(description = "门店")
    private SystemStore systemStore;

    @Schema(description = "店员名称")
    private String staffName;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "核销开关")
    private Integer verifyStatus;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
