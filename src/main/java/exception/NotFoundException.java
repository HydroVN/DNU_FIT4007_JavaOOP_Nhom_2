package main.java.exception;

/**
 * Ngoại lệ dùng khi không tìm thấy dữ liệu.
 */
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
