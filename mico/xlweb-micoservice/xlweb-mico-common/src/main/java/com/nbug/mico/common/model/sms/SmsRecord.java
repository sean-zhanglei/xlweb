package com.nbug.mico.common.model.sms;

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
 * 短信发送记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_sms_record")
@Schema(description="短信发送记录表")
public class SmsRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "短信发送记录编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "短信平台账号")
    private String uid;

    @Schema(description = "接受短信的手机号")
    private String phone;

    @Schema(description = "短信内容")
    private String content;

    @Schema(description = "添加记录ip")
    private String addIp;

    @Schema(description = "短信模板ID")
    private String template;

    @Schema(description = "状态码 100=成功,130=失败,131=空号,132=停机,133=关机,134=无状态")
    private Integer resultcode;

    @Schema(description = "发送记录id")
    private Integer recordId;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "备注")
    private String memo;
}
