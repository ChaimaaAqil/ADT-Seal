package com.adria.adriasign.exception;

public class FileNotValidException extends RuntimeException {
    public FileNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
