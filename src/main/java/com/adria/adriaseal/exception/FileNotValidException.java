package com.adria.adriaseal.exception;

public class FileNotValidException extends RuntimeException {
    public FileNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
    public FileNotValidException(String message) {
        super(message);
    }
}
