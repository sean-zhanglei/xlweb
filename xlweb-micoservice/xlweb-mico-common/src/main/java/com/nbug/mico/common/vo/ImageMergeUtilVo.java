package com.nbug.mico.common.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * 图片合成详情

 */
@Data
public class ImageMergeUtilVo {

    @Schema(description = "图片地址", required = true)
    @NotBlank(message = "图片地址必须填写")
    private String path; //地址

    @Schema(description = "x轴", required = true)
    @Min(value = 0, message = "x轴至少为0")
    private int x; //x轴

    @Schema(description = "y轴", required = true)
    @Min(value = 0, message = "y轴至少为0")
    private int y; //y轴
}
