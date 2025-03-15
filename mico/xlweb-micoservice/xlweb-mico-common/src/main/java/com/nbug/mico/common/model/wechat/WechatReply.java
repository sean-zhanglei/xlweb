package com.nbug.mico.common.model.wechat;

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
 * 微信关键字回复表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_wechat_reply")
@Schema(description="微信关键字回复表")
public class WechatReply implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "微信关键字回复id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "回复类型")
    private String type;

    @Schema(description = "回复数据")
    private String data;

    @Schema(description = "0=不可用  1 =可用")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;


}
