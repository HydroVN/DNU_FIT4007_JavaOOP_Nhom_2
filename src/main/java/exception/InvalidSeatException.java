package main.java.exception;

public class InvalidSeatException extends Exception {

    public InvalidSeatException(String message) {
        super(message);
    }

    public InvalidSeatException(String message, Throwable cause) {
        super(message, cause);
    }
}