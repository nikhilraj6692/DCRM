package com.smp.app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FileStorageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Object[] args;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(String message, Object[] args) {
        super(message);
        this.args = args;
    }
}