package com.nbug.common.response;

import com.nbug.common.model.seckill.StoreSeckill;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="SeckillIndexResponse对象", description="秒杀首页响应对象")
public class SeckillIndexResponse {

    @ApiModelProperty(value = "秒杀时段信息")
    private SecKillResponse secKillResponse;

    @ApiModelProperty(value = "秒杀商品信息")
    private List<StoreSeckill> productList;
}
