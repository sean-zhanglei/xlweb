package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 附近的门店

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="附近的门店")
public class StoreNearRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "经度")
    private String latitude;

    @Schema(description = "纬度")
    private String longitude;
}
