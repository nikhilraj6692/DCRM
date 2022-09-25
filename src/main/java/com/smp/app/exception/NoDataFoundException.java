package com.smp.app.exception;

import java.util.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * If no data available than this exception will be raised.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NoDataFoundException extends RuntimeException {

    private static final long serialVersionUID = 8328432062507384351L;

    private String message;
    private Object[] args;
    private Locale locale;

    public NoDataFoundException() {
    }

    public NoDataFoundException(String message) {
        this.message = message;
    }

    public NoDataFoundException(String message, Object[] args) {
        this.message = message;
        this.args = args;
    }

    public NoDataFoundException(String message, Object[] args, Locale locale) {
        this.message = message;
        this.args = args;
        this.locale = locale;
    }
}
