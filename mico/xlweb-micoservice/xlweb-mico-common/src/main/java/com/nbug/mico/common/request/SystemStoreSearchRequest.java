package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * 提货点搜索

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_store")
@Schema( description="提货点搜索")
public class SystemStoreSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "搜索关键字，支持 门店名称|简介|手机号码||省市区|详细地址")
    private String keywords;

    @Schema(description = "状态，0隐藏，1显示，2回收站", example = "1")
    @Range(min = 0, max = 2, message = "未知的状态")
    private int status;

}
