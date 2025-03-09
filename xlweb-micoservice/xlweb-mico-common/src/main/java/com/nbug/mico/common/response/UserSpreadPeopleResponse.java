package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 推广用户

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="推广用户")
public class UserSpreadPeopleResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "一级推广人人数")
    private Integer total = 0;

    @Schema(description = "二级推广人人数")
    private Integer totalLevel = 0;

    @Schema(description = "推广人列表")
    private List<UserSpreadPeopleItemResponse> spreadPeopleList;

    @Schema(description = "推广人总人数")
    private Integer count = 0;
}
