package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户签到信息响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户签到信息响应对象")
public class UserSignInfoResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "连续签到天数")
    private Integer signNum;

    @Schema(description = "累计签到次数")
    private Integer sumSignDay;

    @Schema(description = "今天是否签到")
    private Boolean isDaySign;

    @Schema(description = "昨天是否签到")
    private Boolean isYesterdaySign;

    @Schema(description = "用户积分")
    private Integer integral;

}
