package com.nbug.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Xlweb异常类

 */
@Slf4j
@RestControllerAdvice
public class XlwebException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public XlwebException() {}

    public XlwebException(String message) {
        super(message);
    }
}
