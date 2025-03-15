package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * H5 砍价公共请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="砍价公共请求对象")
public class BargainFrontRequest {

    @Schema(description = "砍价商品ID", required = true)
    @NotNull(message = "砍价商品编号不能为空")
    private Integer bargainId;

    @Schema(description = "用户砍价活动ID")
    private Integer bargainUserId;

    @Schema(description = "用户砍价活动Uid")
    private Integer bargainUserUid;
}
