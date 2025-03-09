package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 快递公司编辑请求体

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_express")
@Schema( description="快递公司编辑请求体")
public class ExpressUpdateRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "快递公司id")
    @NotNull(message = "快递公司id不能为空")
    private Integer id;

    @Schema(description = "账号")
    private String account;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "网点名称")
    private String netName;

    @Schema(description = "排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;

//    @Schema(description = "是否显示")
//    @NotNull(message = "是否显示不能为空")
//    private Boolean isShow;

    @Schema(description = "是否可用")
    @NotNull(message = "是否可用不能为空")
    private Boolean status;
}
