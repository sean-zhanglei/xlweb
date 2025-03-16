package com.nbug.mico.common.model.logger;

import com.baomidou.mybatisplus.annotation.TableName;
import com.nbug.mico.common.enums.UserTypeEnum;
import com.nbug.mico.common.model.dataobject.BaseDO;
import lombok.Data;
import lombok.ToString;

/**
 * 登录日志表
 *
 * 注意，包括登录和登出两种行为
 *
 * @author NBUG
 */
@TableName("eb_system_login_log")
@Data
@ToString(callSuper = true)
public class LoginLog extends BaseDO {

    /**
     * 日志主键
     */
    private Long id;
    /**
     * 日志类型
     *
     */
    private Integer logType;
    /**
     * 链路追踪编号
     */
    private String traceId;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 用户账号
     *
     * 冗余，因为账号可以变更
     */
    private String username;
    /**
     * 登录结果
     *
     */
    private Integer result;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

}
