package com.nbug.mico.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 门店店员表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="后台登录页面轮播图")
public class SystemGroupDataAdminLoginBannerResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "图片")
    private String pic;

}
