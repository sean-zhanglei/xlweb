package com.nbug.mico.common.response;

import com.nbug.mico.common.model.combination.StoreCombination;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 拼团首页响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="拼团首页响应对象")
public class CombinationIndexResponse {

    @Schema(description = "拼团3位用户头像")
    private List<String> avatarList;

    @Schema(description = "拼团总人数")
    private Integer totalPeople;

    @Schema(description = "拼团商品列表")
    private List<StoreCombination> productList;

}
