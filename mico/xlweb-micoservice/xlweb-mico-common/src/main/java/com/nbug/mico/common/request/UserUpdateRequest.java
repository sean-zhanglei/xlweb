package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户更新请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="用户更新请求对象")
public class UserUpdateRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "uid")
    private Integer uid;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "生日")
    private String birthday;

    @Schema(description = "身份证号码")
    private String cardId;

    @Schema(description = "用户备注")
    private String mark;

    @Schema(description = "状态是否正常， 0 = 禁止， 1 = 正常")
    @NotNull(message = "状态不能为空")
    private Boolean status;

    @Schema(description = "详细地址")
    private String addres;

//    @Schema(description = "等级")
//    private Integer level;

    @Schema(description = "用户分组id")
    private String groupId;

    @Schema(description = "用户标签id")
    private String tagId;

    @Schema(description = "是否为推广员")
    @NotNull(message = "是否为推广员不能为空")
    private Boolean isPromoter;

}
