package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;

/**
 * 拼团商品列表header响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="拼团商品列表header响应对象")
public class CombinationHeaderResponse {

    @Schema(description = "拼团7位用户头像")
    private List<String> avatarList;

    @Schema(description = "拼团总人数")
    private Integer totalPeople;

    @Schema(description = "拼团banner数组")
    private List<HashMap<String, Object>> bannerList;
}
