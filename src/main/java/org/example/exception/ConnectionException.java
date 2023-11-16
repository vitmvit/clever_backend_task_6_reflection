package org.example.exception;

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
        super("Connection is lost");
    }

    public ConnectionException(String message) {
        super("Connection is lost: " + message);
    }
}