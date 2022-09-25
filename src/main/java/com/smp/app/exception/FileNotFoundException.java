package com.smp.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Object[] args;

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(String message, Object[] args) {
        super(message);
        this.args = args;
    }
}