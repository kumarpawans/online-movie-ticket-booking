package com.psp.test.database;

public class TheaterCreationException extends Exception {
    public TheaterCreationException(String message) {
        super(message);
    }

    public TheaterCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

