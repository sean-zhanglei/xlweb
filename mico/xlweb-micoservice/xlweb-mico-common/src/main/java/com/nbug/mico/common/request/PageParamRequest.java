package com.nbug.mico.common.request;

import com.nbug.mico.common.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 分页公共请求对象

 */
@Data
public class PageParamRequest {

    @Schema(description = "页码", example= Constants.DEFAULT_PAGE + "")
    private int page = Constants.DEFAULT_PAGE;

    @Schema(description = "每页数量", example = Constants.DEFAULT_LIMIT + "")
    private int limit = Constants.DEFAULT_LIMIT;

}
