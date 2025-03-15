package com.nbug.mico.common.model.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user")
@Schema(description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户id")
    @TableId(value = "uid", type = IdType.AUTO)
    private Integer uid;

    @Schema(description = "用户账号")
    private String account;

    @Schema(description = "用户密码")
    @JsonIgnore
    private String pwd;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "生日")
    private String birthday;

    @Schema(description = "身份证号码")
    private String cardId;

    @Schema(description = "用户备注")
    private String mark;

    @Schema(description = "合伙人id")
    private Integer partnerId;

    @Schema(description = "用户分组id")
    private String groupId;

    @Schema(description = "用户标签id")
    private String tagId;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "性别")
    private int sex;

    @Schema(description = "国家")
    private String country;

    @Schema(description = "添加ip")
    private String addIp;

    @Schema(description = "最后一次登录ip")
    private String lastIp;

    @Schema(description = "用户余额")
    private BigDecimal nowMoney;

    @Schema(description = "佣金金额")
    private BigDecimal brokeragePrice;

    @Schema(description = "用户剩余积分")
    private Integer integral;

    @Schema(description = "用户剩余经验")
    private Integer experience;

    @Schema(description = "连续签到天数")
    private Integer signNum;

    @Schema(description = "1为正常，0为禁止")
    private Boolean status;

    @Schema(description = "等级")
    private Integer level;

    @Schema(description = "推广人id")
    private Integer spreadUid;

    @Schema(description = "推广员关联时间")
    private Date spreadTime;

    @Schema(description = "用户类型")
    private String userType;

    @Schema(description = "是否为推广员")
    private Boolean isPromoter;

    @Schema(description = "用户购买次数")
    private Integer payCount;

    @Schema(description = "下级人数")
    private Integer spreadCount;

    @Schema(description = "详细地址")
    private String addres;

    @Schema(description = "管理员编号 ")
    private Integer adminid;

    @Schema(description = "用户登陆类型，h5,wechat,routine")
    private String loginType;

    @Schema(description = "创建时间")
    private Date updateTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "最后一次登录时间")
    private Date lastLoginTime;

    @Schema(description = "最后一次登录时间")
    private Date cleanTime;

    @Schema(description = "用户推广等级")
    private String path;

    @Schema(description = "是否关注公众号")
    private Boolean subscribe;

    @Schema(description = "成为分销员时间")
    private Date promoterTime;
}
