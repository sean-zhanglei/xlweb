package com.nbug.common.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 核销

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SystemWriteOffOrderSearchRequest对象", description="核销订单搜索")
public class SystemWriteOffOrderSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "核销点ID")
    private Integer storeId;

    @ApiModelProperty(value = "时间")
    private String dateLimit;

    @ApiModelProperty(value = "关键字")
    private String keywords;

}
