package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 组合数据表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_group")
@Schema( description="组合数据表")
public class SystemGroupSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "关键字")
    private String keywords;
}
