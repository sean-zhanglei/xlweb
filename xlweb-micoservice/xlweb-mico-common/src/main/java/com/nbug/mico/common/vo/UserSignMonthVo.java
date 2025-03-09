package com.nbug.mico.common.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 签到记录表

 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("eb_user_sign")
@Schema(description="签到记录表")
public class UserSignMonthVo implements Serializable {

    private static final long serialVersionUID=1L;

    public UserSignMonthVo() {}
    public UserSignMonthVo(String month, List<UserSignVo> list) {
        this.month = month;
        this.list = list;
    }

    @Schema(description = "月")
    private String month;

    @Schema(description = "签到列表")
    private List<UserSignVo> list;
}
