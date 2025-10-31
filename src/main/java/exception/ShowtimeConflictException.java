package main.java.exception;

public class ShowtimeConflictException extends Exception {

    public ShowtimeConflictException(String message) {
        super(message);
    }

    public ShowtimeConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}