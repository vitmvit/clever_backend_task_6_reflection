package org.example.exception;

public class ReportGenerateException extends RuntimeException {
    public ReportGenerateException(Exception e) {
        super(e);
    }
}
