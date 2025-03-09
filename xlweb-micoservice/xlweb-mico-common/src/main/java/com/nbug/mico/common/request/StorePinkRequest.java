package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 拼团表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_pink")
@Schema( description="拼团表")
public class StorePinkRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "拼团ID")
    private Integer id;

    @Schema(description = "拼团商品id")
    private Integer cid;
}
