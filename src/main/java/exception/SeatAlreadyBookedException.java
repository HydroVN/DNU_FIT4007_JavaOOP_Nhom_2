package main.java.exception;

public class SeatAlreadyBookedException extends  Exception {
    public SeatAlreadyBookedException(String message, Throwable cause) {
        super(message, cause);
    }
}
