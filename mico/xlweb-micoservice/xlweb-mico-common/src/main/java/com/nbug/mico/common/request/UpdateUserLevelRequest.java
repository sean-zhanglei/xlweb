package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 更新用户会员等级

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="更新用户会员等级对象")
public class UpdateUserLevelRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "uid")
    @NotNull(message = "用户id不能为空")
    private Integer uid;

    @Schema(description = "会员等级")
    @NotNull(message = "等级id不能为空")
    private Integer levelId;

    @Schema(description = "是否扣减积分，true-扣减，false-不扣减")
    private Boolean isSub;

}
