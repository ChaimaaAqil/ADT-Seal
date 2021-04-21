package com.adria.adriasign.exception;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    public GenericException(String message, Throwable cause) {
        super(message, cause);
    }

}
