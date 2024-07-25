package com.bolete.openai.exceptions;

public class InvalidGPTResponseException extends RuntimeException {
    public InvalidGPTResponseException(String message) {
        super(message);
    }
}
