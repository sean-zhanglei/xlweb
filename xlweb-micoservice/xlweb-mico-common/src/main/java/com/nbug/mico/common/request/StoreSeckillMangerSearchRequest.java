package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商品秒杀配置

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("StoreSeckillMangerSearchRequest")
@Schema( description="商品秒杀配置")
public class StoreSeckillMangerSearchRequest {

    @Schema(description = "秒杀名称")
    private String name;

    @Schema(description = "状态 0=关闭 1=开启")
    private Integer status;
}
