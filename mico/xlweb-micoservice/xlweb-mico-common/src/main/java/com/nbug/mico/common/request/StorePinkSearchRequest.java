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
public class StorePinkSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "状态1进行中2已完成3未完成")
    private Integer status;

    @Schema(description = "today,yesterday,lately7,lately30,month,year,/yyyy-MM-dd hh:mm:ss,yyyy-MM-dd hh:mm:ss/")
    private String dateLimit;

}
