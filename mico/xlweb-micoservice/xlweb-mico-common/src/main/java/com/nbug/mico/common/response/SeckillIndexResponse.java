package com.nbug.mico.common.response;

import com.nbug.mico.common.model.seckill.StoreSeckill;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 秒杀首页响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="秒杀首页响应对象")
public class SeckillIndexResponse {

    @Schema(description = "秒杀时段信息")
    private SecKillResponse secKillResponse;

    @Schema(description = "秒杀商品信息")
    private List<StoreSeckill> productList;
}
