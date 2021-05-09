package com.adria.adriaseal.exception;

public class TransactionNotFinishedException extends RuntimeException{
    public TransactionNotFinishedException(String message) {
        super(message);
    }
}