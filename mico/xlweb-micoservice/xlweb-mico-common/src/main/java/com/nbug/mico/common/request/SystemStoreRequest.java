package com.nbug.mico.common.request;

import com.nbug.mico.common.constants.RegularConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 门店自提

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="提货点")
public class SystemStoreRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "门店名称")
    @NotBlank(message = "请填写门店名称")
    @Length(max = 100, message = "门店名称不能超过100个字符")
    private String name;

    @Schema(description = "简介")
    private String introduction;

    @Schema(description = "手机号码")
    @Pattern(regexp = RegularConstants.PHONE_TWO, message = "手机号码格式错误")
    private String phone;

    @Schema(description = "提货点地址省市区")
    @NotBlank(message = "提货点地址")
    @Length(max = 255, message = "提货点地址不能超过255个字符")
    private String address;

    @Schema(description = "详细地址")
    @NotBlank(message = "请填写详细地址")
    @Length(max = 255, message = "详细地址不能超过255个字符")
    private String detailedAddress;

    @Schema(description = "每日营业开关时间")
    private String dayTime;

    @Schema(description = "门店logo")
    @NotBlank(message = "请上传门店logo")
    @Length(max = 255, message = "门店logo不能超过255个字符")
    private String image;

    @Schema(description = "纬度")
    @NotBlank(message = "请选择经纬度")
    private String latitude;

    @Schema(description = "经度", example = "1,2")
    private String longitude;

    @Schema(description = "核销有效日期")
    private String validTime;
}
