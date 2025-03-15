package com.nbug.mico.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统通知详情相应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="系统通知详情相应对象")
public class NotificationInfoResponse implements Serializable {

    private static final long serialVersionUID = -3214167698001861141L;

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "模板id(公用)")
    private String tempId;

    @Schema(description = "模板说明(短信)")
    private String title;

    @Schema(description = "模板编号(公用)")
    private String tempKey;

    @Schema(description = "内容(公用)")
    private String content;

    @Schema(description = "模板名")
    private String name;

    @Schema(description = "状态,1-开启，2-关闭")
    private Integer status;
}
