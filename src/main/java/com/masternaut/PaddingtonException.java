package com.masternaut;

public class PaddingtonException extends RuntimeException {
    public PaddingtonException() {
        super();
    }

    public PaddingtonException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaddingtonException(Throwable cause) {
        super(cause);
    }

    public PaddingtonException(String message) {
        super(message);
    }
}
