package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbug.mico.common.annotation.StringContains;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user")
@Schema( description="用户表")
public class UserSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;


    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "时间")
    private String dateLimit;

    @Schema(description = "用户分组")
    private String groupId;

    @Schema(description = "用户标签")
    private String labelId;

    @Schema(description = "用户登陆类型，h5 = h5， wechat = wechat，routine = routine", allowableValues = "range[h5,wechat,routine]")
    @StringContains(limitValues = {"h5","wechat","routine"}, message = "请选择正确的用户登录类型")
    private String userType;

    @Schema(description = "状态是否正常， 0 = 禁止， 1 = 正常")
    private Boolean status = null;

    @Schema(description = "是否为推广员， 0 = 禁止， 1 = 正常")
    private Boolean isPromoter = null;

    @Schema(description = "消费情况")
    private String payCount;

    @Schema(description = "等级")
    private String level;

    //时间类型
    @Schema(description = "访问情况， 0 = 全部， 1 = 首次， 2 = 访问过， 3 = 未访问", allowableValues = "range[0,1,2,3]")
    @NotNull(message = "访问情况不能为空")
    private Integer accessType = 0;

    @Schema(description = "国家，中国CN，其他OTHER")
    private String country;

    @Schema(description = "省份")
    private String province;

    @Schema(description = "城市")
    private String city;

    @Schema(description = "性别，0未知，1男，2女，3保密")
    private String sex;
}
