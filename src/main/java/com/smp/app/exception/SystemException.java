package com.smp.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object[] args;

    public SystemException(String message) {
        super(message);
    }

    public SystemException() {
    }

    public SystemException(String message, Object[] args) {
        super(message);
        this.args = args;
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

}
