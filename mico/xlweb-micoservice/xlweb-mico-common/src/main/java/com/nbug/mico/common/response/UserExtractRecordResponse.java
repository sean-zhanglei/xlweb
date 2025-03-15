package com.nbug.mico.common.response;

import com.nbug.mico.common.model.finance.UserExtract;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 提现记录响应对象
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="用户提现记录对象")
public class UserExtractRecordResponse {

    private static final long serialVersionUID=1L;

    public UserExtractRecordResponse() {}
    public UserExtractRecordResponse(String date, List<UserExtract> list) {
        this.date = date;
        this.list = list;
    }

    @Schema(description = "月份")
    private String date;

    @Schema(description = "数据")
    private List<UserExtract> list;
}
