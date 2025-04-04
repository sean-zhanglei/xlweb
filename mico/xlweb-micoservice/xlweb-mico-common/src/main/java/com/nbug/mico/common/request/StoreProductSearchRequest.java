package com.nbug.mico.common.request;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 商品表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_store_product")
@Schema( description="商品表")
public class StoreProductSearchRequest implements Serializable {

    private static final long serialVersionUID=1L;

    @Schema(description = "类型（1：出售中（已上架），2：仓库中（未上架），3：已售罄，4：警戒库存，5：回收站）")
    @NotNull(message = "商品类型不能为空")
    @Range(min = 1, max = 5, message = "未知的商品类型")
    private int type;

    @Schema(description = "分类ID， 多个逗号分隔")
    private String cateId;

    @Schema(description = "关键字搜索， 支持(商品名称, 商品简介, 关键字, 商品条码)")
    private String keywords;

}
