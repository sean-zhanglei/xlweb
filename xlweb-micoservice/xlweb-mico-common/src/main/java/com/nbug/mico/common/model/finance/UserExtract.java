package com.nbug.mico.common.model.finance;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户 提现表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_extract")
@Schema(description="用户提现表")
public class UserExtract implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    @Schema(description = "名称")
    private String realName;

    @Schema(description = "bank = 银行卡 alipay = 支付宝 weixin=微信")
    private String extractType;

    @Schema(description = "银行卡")
    private String bankCode;

    @Schema(description = "开户地址")
    private String bankAddress;

    @Schema(description = "支付宝账号")
    private String alipayCode;

    @Schema(description = "提现金额")
    private BigDecimal extractPrice;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "金额")
    private BigDecimal balance;

    @Schema(description = "无效原因")
    private String failMsg;

    @Schema(description = "-1 未通过 0 审核中 1 已提现")
    private Integer status;

    @Schema(description = "微信号")
    private String wechat;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    @Schema(description = "失败时间")
    private Date failTime;

    @Schema(description = "银行名称")
    private String bankName;

    @Schema(description = "银行名称")
    private String qrcodeUrl;

    @Transient
    @TableField(exist = false)
    private String nickName;
}
