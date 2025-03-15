package com.nbug.mico.common.response;

import com.nbug.mico.common.vo.SystemStoreNearVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 附近的门店
 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="附近的门店")
public class StoreNearResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "腾讯地图key")
    private String tengXunMapKey;

    @Schema(description = "附近的门店列表", required = true)
    private List<SystemStoreNearVo> list;


}
