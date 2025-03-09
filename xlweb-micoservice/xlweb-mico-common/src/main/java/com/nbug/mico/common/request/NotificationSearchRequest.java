package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 系统通知查询对象
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="系统通知查询对象")
public class NotificationSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "发送类型（1：通知会员，2：通知平台）")
    @Range(min = 1, max = 2, message = "未知的发送类型")
    private Integer sendType;

}
