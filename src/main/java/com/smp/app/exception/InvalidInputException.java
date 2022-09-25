package com.smp.app.exception;

import java.util.Locale;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * If given input is not valid than this exception will be raised.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = 8328432062507384351L;

    private String message;

    private Object[] args;

    private Locale locale;

    public InvalidInputException(String rawMessage) {
        this.message = rawMessage;
    }

    public InvalidInputException(String rawMessage, Object[] args) {
        this.message = rawMessage;
        this.args = args;
    }


    public InvalidInputException(String rawMessage, Locale locale, Object[] args) {
        this.message = rawMessage;
        this.locale = locale;
        this.args = args;
    }
}