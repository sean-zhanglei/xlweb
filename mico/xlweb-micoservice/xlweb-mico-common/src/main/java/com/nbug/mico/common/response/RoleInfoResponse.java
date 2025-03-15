package com.nbug.mico.common.response;

import com.nbug.mico.common.vo.MenuCheckVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色详情响应对象

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description="角色详情响应对象")
public class RoleInfoResponse implements Serializable {

    private static final long serialVersionUID = -6123516979502057197L;

    @Schema(description = "角色id")
    private Integer id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "状态：0-关闭，1-正常")
    private Boolean status;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "修改时间")
    private Date updateTime;

    @Schema(description = "修改时间")
    private List<MenuCheckVo> menuList;

}
