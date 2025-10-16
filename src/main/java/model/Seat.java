package main.java.model;

public abstract class Seat {
    protected int seatId;
    protected int roomId;
    protected String row;
    protected int column;
    protected String seatType;

    public Seat(int seatId, int roomId, String row, int column, String seatType) {
        this.seatId = seatId;
        this.roomId = roomId;
        this.row = row;
        this.column = column;
        this.seatType = seatType;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getSeatType() {
        return seatType;
    }

    // Phương thức tính giá ghế — sẽ override ở lớp con
    public abstract double getPrice(double basePrice);

    @Override
    public String toString() {
        return String.format("Ghế [Mã=%d, Phòng=%d, Hàng=%s, Cột=%d, Loại=%s]",
                seatId, roomId, row, column, seatType);
    }
}
