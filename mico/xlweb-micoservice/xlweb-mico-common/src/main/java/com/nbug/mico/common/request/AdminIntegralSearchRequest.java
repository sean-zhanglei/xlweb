package com.nbug.mico.common.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 后台积分查询请求对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="后台积分查询请求对象")
public class AdminIntegralSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "添加时间")
    private String dateLimit;

    @Schema(description = "搜索关键字")
    private String keywords;

    @Schema(description = "用户id")
    private Integer uid;
}
