package com.nbug.mico.common.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 系统附件类

 */
@Data
public class SystemAttachmentRequest {
    private static final long serialVersionUID=1L;

    private Integer attId;

    @Schema(description = "附件名称")
    private String name;

    @Schema(description = "附件路径")
    private String attDir;

    @Schema(description = "压缩图片路径")
    private String sattDir;

    @Schema(description = "服务器上存储的绝对地址")
    private String rootDir;

    @Schema(description = "附件大小")
    private String attSize;

    @Schema(description = "附件类型")
    private String attType;

    @Schema(description = "模块，store")
    private String model;

    @Schema(description = "图片上传类型 1本地 2七牛云 3OSS 4COS ")
    private Integer imageType;

    @Schema(description = "图片上传模块类型 1 后台上传 2 用户生成")
    private Integer moduleType;

    @Schema(description = "创建时间")
    private Date createTime;
}
