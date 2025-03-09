package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 门店店员表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_store_staff")
@Schema( description="门店店员表")
public class SystemStoreStaffRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "管理员 id")
    @Min(value = 1, message = "请选择管理员")
    private Integer uid;

    @Schema(description = "管理员头像")
    private String avatar;

    @Schema(description = "提货点id")
    @Min(value = 1, message = "请选择提货点")
    private Integer storeId;

    @Schema(description = "核销员 名称 [昵称]")
    @NotBlank(message = "核销员名称不能为空")
    @Length(max = 64,message = "核销员名称不能超过64个字符")
    private String staffName;

    @Schema(description = "手机号码")
    private String phone;

    @Schema(description = "核销开关")
    private boolean verifyStatus = false;

    @Schema(description = "状态")
    private boolean status = false;


}
