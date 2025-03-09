package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 用户表 推广人 Request
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="推广用户")
public class UserSpreadPeopleRequest implements Serializable {

    private static final long serialVersionUID=1L;


    @Schema(description = "推荐人类型|0=一级|1=二级", allowableValues = "range[0,1]")
    @Range(min = 0, max = 1, message = "推荐人类型必须在 0（一级），1（二级） 中选择")
    private Integer grade = 0;

    @Schema(description = "搜索关键字")
    private String keyword;

    @Schema(description = "排序, 排序|childCount=团队排序,numberCount=金额排序,orderCount=订单排序", allowableValues = "range[childCount,numberCount,orderCount]")
    private String sortKey;

    @Schema(description = "排序值 DESC ASC")
    private String isAsc = "DESC";
}
