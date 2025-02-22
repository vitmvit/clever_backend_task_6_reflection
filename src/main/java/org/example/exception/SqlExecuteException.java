package org.example.exception;

public class SqlExecuteException extends RuntimeException {

    public SqlExecuteException(Throwable ex) {
        super(ex);
    }
}