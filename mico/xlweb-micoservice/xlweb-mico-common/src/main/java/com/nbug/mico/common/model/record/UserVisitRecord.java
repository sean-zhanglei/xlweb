package com.nbug.mico.common.model.record;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户访问记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_visit_record")
@Schema(description="用户访问记录表")
public class UserVisitRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "日期")
    private String date;

    @Schema(description = "用户uid")
    private Integer uid;

    @Schema(description = "访问类型:1-首页，2-详情页，3-营销活动详情页，4-个人中心")
    private Integer visitType;


}
