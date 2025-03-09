package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.List;

/**
 * 砍价商品列表header响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="砍价商品列表header响应对象")
public class BargainHeaderResponse {

    @Schema(description = "参与砍价总人数")
    private Integer bargainTotal;

    @Schema(description = "砍价成功列表（默认7条）")
    private List<HashMap<String, Object>> bargainSuccessList;

}
