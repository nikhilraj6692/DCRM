package com.smp.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@EqualsAndHashCode(callSuper = false)
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -5754907642110583004L;

    public ForbiddenException() {

    }

    /**
     * Constructs a <code>UsernameNotFoundException</code> with the specified message.
     *
     * @param msg the detail message.
     */
    public ForbiddenException(String msg) {
        super(msg);
    }

    /**
     * Constructs a {@code UsernameNotFoundException} with the specified message and root cause.
     *
     * @param msg the detail message.
     * @param t   root cause
     */
    public ForbiddenException(String msg, Throwable t) {
        super(msg, t);
    }
}
