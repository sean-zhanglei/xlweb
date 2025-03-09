package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分类表

 */
@Data
public class SystemCityTreeVo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "城市id")
    private Integer cityId;

    @Schema(description = "省市级别")
    private Integer level;

    @Schema(description = "父级id")
    private Integer parentId;

    @Schema(description = "区号")
    private String areaCode;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "合并名称")
    private String mergerName;

    @Schema(description = "经度")
    private String lng;

    @Schema(description = "纬度")
    private String lat;

    @Schema(description = "是否展示")
    private Boolean isShow;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) //属性为 空（""）[] 或者为 NULL 都不序列化
    private List<SystemCityTreeVo> child = new ArrayList<>();
}
