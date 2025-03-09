package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 微信关键字回复表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_wechat_reply")
@Schema( description="微信关键字回复表")
public class WechatReplySearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "关键字")
    private String keywords;

    @Schema(description = "回复类型 text=文本  image =图片  news =图文  voice =音频"  , example = "text")
    private String type;
}
