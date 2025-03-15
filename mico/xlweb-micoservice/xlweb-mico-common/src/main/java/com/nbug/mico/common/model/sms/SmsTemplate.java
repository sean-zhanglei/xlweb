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
 * 短信模板表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_sms_template")
@Schema(description="短信模板表")
public class SmsTemplate implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "短信模板id")
    private String tempId;

    @Schema(description = "模板类型")
    private Integer tempType;

    @Schema(description = "模板说明")
    private String title;

    @Schema(description = "类型")
    private String type;

    @Schema(description = "模板编号")
    private String tempKey;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "短息内容")
    private String content;

    @Schema(description = "添加时间")
    private Date createTime;


}
