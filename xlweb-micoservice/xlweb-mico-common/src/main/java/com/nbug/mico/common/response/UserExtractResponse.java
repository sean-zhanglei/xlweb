package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 提现数据用于分销

 */
@Data
public class UserExtractResponse {
    // 提现数据总额
    @Schema(description = "体现数据总额")
    private BigDecimal extractCountPrice;
    // 提现次数
    @Schema(description = "提现次数")
    private Integer extractCountNum;
    // 提现用户id
//    private Integer euid;
}
