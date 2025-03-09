package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 评论添加对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema( description="评论添加对象")
public class StoreProductReplyAddRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "订单编号， 移动端必须传递此参数")
    private String orderNo;

    @Schema(description = "用户id， 后端必须传递此参数")
    private Integer userId;

    @Schema(description = "商品id", required = true)
    @Min(value = 1, message = "请选择商品")
    private Integer productId;

    @Schema(description = "商品 属性id")
    private String unique;

    @Schema(description = "商品分数", example = "5", required = true)
    @Range(min = 1, max = 5, message = "商品分数为1-5")
    private Integer productScore;

    @Schema(description = "服务分数", example = "5", required = true)
    @Range(min = 1, max = 5, message = "服务分数为1-5")
    private Integer serviceScore;

    @Schema(description = "评论内容", required = true)
    @NotBlank(message = "请填写评论内容")
    @Length(max = 512, message = "评论内容长度不能超过512个字符")
    private String comment;

    @Schema(description = "评论图片", required = true)
    private String pics;

    @Schema(description = "评论人头像 [虚拟评论参数]")
    private String avatar;

    @Schema(description = "评论人昵称 [虚拟评论参数]")
    private String nickname;

    @Schema(description = "商品规格属性值，多规格时用英文逗号拼接")
    private String sku;
}
