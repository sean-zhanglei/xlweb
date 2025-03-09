package com.nbug.mico.common.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户地址表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_address")
@Schema(description="用户地址表")
public class UserAddress implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户地址id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "用户id")
    private Integer uid;

    @Schema(description = "收货人姓名")
    private String realName;

    @Schema(description = "收货人电话")
    private String phone;

    @Schema(description = "收货人所在省")
    private String province;

    @Schema(description = "收货人所在市")
    private String city;

    @Schema(description = "城市id")
    private Integer cityId;

    @Schema(description = "收货人所在区")
    private String district;

    @Schema(description = "收货人详细地址")
    private String detail;

    @Schema(description = "邮编")
    private Integer postCode;

    @Schema(description = "经度")
    private String longitude;

    @Schema(description = "纬度")
    private String latitude;

    @Schema(description = "是否默认")
    private Boolean isDefault;

    @TableLogic
    @Schema(description = "是否删除")
    private Boolean isDel;

    @Schema(description = "创建时间")
    private Date updateTime;

    @Schema(description = "创建时间")
    private Date createTime;


}
