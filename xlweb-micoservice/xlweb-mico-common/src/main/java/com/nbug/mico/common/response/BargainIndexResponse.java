package com.nbug.mico.common.response;

import com.nbug.mico.common.model.bargain.StoreBargain;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 砍价首页响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="砍价首页响应对象")
public class BargainIndexResponse {

    @Schema(description = "拼团商品列表")
    private List<StoreBargain> productList;

}
