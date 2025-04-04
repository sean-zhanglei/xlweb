package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 微信关键字回复表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_wechat_reply")
@Schema( description="微信关键字回复表")
public class WechatReplyRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "关键字id")
    private Integer id;

    @Schema(description = "关键字")
    @NotBlank(message = "请填写关键字, 关注 = subscribe， 默认 = default")
    private String keywords;

    @Schema(description = "回复类型 text=文本  image =图片  news =图文  voice =音频"  , example = "text")
    @NotBlank(message = "请选择回复类型")
    private String type;

    @Schema(description = "回复数据")
    @NotBlank(message = "请填写回复数据")
    private String data;

    @Schema(description = "回复状态 0=不可用  1 =可用", example = "1")
    @NotNull(message = "请选择回复状态")
    private Boolean status;
}
