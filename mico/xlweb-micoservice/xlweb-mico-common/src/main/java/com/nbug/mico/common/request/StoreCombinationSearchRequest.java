package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 拼团商品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_combination")
@Schema( description="拼团商品表")
public class StoreCombinationSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "搜索关键字 商品id或者名称")
    private String keywords;

    @Schema(description = "砍价状态 0(不开启)  1(开启)")
    private Integer isShow;

}
