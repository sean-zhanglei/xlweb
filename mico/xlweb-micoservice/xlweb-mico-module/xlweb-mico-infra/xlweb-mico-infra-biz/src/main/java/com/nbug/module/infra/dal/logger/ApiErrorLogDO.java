package com.nbug.module.infra.dal.logger;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nbug.mico.common.enums.UserTypeEnum;
import com.nbug.module.infra.enums.logger.ApiErrorLogProcessStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import java.time.LocalDateTime;

/**
 * API 异常数据
 *
 * @author NUBG
 */
@TableName("eb_api_error_log")
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiErrorLogDO {

    /**
     * {@link #requestParams} 的最大长度
     */
    public static final Integer REQUEST_PARAMS_MAX_LENGTH = 8000;

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 链路追踪编号
     *
     * 一般来说，通过链路追踪编号，可以将访问日志，错误日志，链路追踪日志，logger 打印日志等，结合在一起，从而进行排错。
     */
    private String traceId;
    /**
     * 用户类型
     *
     * 枚举 {@link UserTypeEnum}
     */
    private Integer userType;
    /**
     * 应用名
     *
     * 目前读取 spring.application.name
     */
    private String applicationName;

    // ========== 请求相关字段 ==========

    /**
     * 请求方法名
     */
    private String requestMethod;
    /**
     * 访问地址
     */
    private String requestUrl;
    /**
     * 请求参数
     *
     * query: Query String
     * body: Quest Body
     */
    private String requestParams;
    /**
     * 用户 IP
     */
    private String userIp;
    /**
     * 浏览器 UA
     */
    private String userAgent;

    // ========== 异常相关字段 ==========

    /**
     * 异常发生时间
     */
    private LocalDateTime exceptionTime;
    /**
     * 异常名
     *
     * {@link Throwable#getClass()} 的类全名
     */
    private String exceptionName;
    /**
     * 异常导致的消息
     *
     * {@link cn.hutool.core.exceptions.ExceptionUtil#getMessage(Throwable)}
     */
    private String exceptionMessage;
    /**
     * 异常导致的根消息
     *
     * {@link cn.hutool.core.exceptions.ExceptionUtil#getRootCauseMessage(Throwable)}
     */
    private String exceptionRootCauseMessage;
    /**
     * 异常的栈轨迹
     *
     * {@link org.apache.commons.lang3.exception.ExceptionUtils#getStackTrace(Throwable)}
     */
    private String exceptionStackTrace;
    /**
     * 异常发生的类全名
     *
     * {@link StackTraceElement#getClassName()}
     */
    private String exceptionClassName;
    /**
     * 异常发生的类文件
     *
     * {@link StackTraceElement#getFileName()}
     */
    private String exceptionFileName;
    /**
     * 异常发生的方法名
     *
     * {@link StackTraceElement#getMethodName()}
     */
    private String exceptionMethodName;
    /**
     * 异常发生的方法所在行
     *
     * {@link StackTraceElement#getLineNumber()}
     */
    private Integer exceptionLineNumber;

    // ========== 处理相关字段 ==========

    /**
     * 处理状态
     *
     * 枚举 {@link ApiErrorLogProcessStatusEnum}
     */
    private Integer processStatus;
    /**
     * 处理时间
     */
    private LocalDateTime processTime;
    /**
     * 处理用户编号
     *
     * 关联 com.nbug.xlweb.adminserver.modules.system.dal.dataobject.user.SysUserDO.SysUserDO#getId()
     */
    private Long processUserId;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    /**
     * 创建者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @TableField(fill = FieldFill.INSERT, jdbcType = JdbcType.VARCHAR)
    private String creator;
    /**
     * 更新者，目前使用 SysUser 的 id 编号
     *
     * 使用 String 类型的原因是，未来可能会存在非数值的情况，留好拓展性。
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, jdbcType = JdbcType.VARCHAR)
    private String updater;
    /**
     * 是否删除
     */
    @TableLogic
    private Boolean deleted;
}
