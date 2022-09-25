package com.smp.app.exception;

import java.util.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * If given input is not match than this exception will be raised.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 8328432062507384351L;

    private String message;

    private Object[] args;

    private Locale locale;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Object[] args) {
        this.message = message;
        this.args = args;
    }


    public BusinessException(String message, Locale locale, Object[] args) {
        this.message = message;
        this.locale = locale;
        this.args = args;
    }
}
