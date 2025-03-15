package com.nbug.mico.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户积分记录响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户积分记录响应对象")
public class UserIntegralRecordResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "记录id")
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

    @Schema(description = "积分")
    private Integer integral;

    @Schema(description = "剩余")
    private Integer balance;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "状态：1-订单创建，2-冻结期，3-完成，4-失效（订单退款）")
    private Integer status;

    @Schema(description = "冻结期时间（天）")
    private Integer frozenTime;

    @Schema(description = "解冻时间")
    private Long thawTime;

    @Schema(description = "添加时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "用户昵称")
    private String nickName;
}
