package com.nbug.mico.common.vo;


import lombok.Data;

/**
 * Base Result

 */
@Data
public class BaseResultResponseVo {
    private Integer errcode;
    private String errmsg;
}
