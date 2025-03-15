package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户账单表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="资金监控对象")
public class MonitorResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "用户账单id")
    private Integer id;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "0 = 支出 1 = 获得")
    private int pm;

    @Schema(description = "账单标题")
    private String title;

    @Schema(description = "明细数字")
    private BigDecimal number;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "用户昵称")
    private String nickName;

}
