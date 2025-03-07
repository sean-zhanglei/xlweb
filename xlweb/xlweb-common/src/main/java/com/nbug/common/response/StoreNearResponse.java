package com.nbug.common.response;

import com.nbug.common.vo.SystemStoreNearVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="StoreNearRequest对象", description="附近的门店")
public class StoreNearResponse implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "腾讯地图key")
    private String tengXunMapKey;

    @ApiModelProperty(value = "附近的门店列表", required = true)
    private List<SystemStoreNearVo> list;


}
