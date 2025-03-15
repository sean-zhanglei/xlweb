package com.nbug.mico.common.model.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 附件管理表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_system_attachment")
@Schema(description="附件管理表")
public class SystemAttachment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "att_id", type = IdType.AUTO)
    private Integer attId;

    @Schema(description = "附件名称")
    private String name;

    @Schema(description = "附件路径")
    private String attDir;

    @Schema(description = "压缩图片路径")
    private String sattDir;

    @Schema(description = "附件大小")
    private String attSize;

    @Schema(description = "附件类型")
    private String attType;

    @Schema(description = "分类ID 0编辑器,1商品图片,2拼团图片,3砍价图片,4秒杀图片,5文章图片,6组合数据图,7前台用户,8微信系列")
    private Integer pid;

    @Schema(description = "图片上传类型 1本地 2七牛云 3OSS 4COS ")
    private Integer imageType;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

}
