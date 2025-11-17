package main.java.model;

public abstract class Seat {
    protected int seatId;
    protected int roomId;
    protected String row;
    protected int column;
    protected int maLoaiGhe;

    public Seat(int seatId, int roomId, String row, int column, int maLoaiGhe) {
        this.seatId = seatId;
        this.roomId = roomId;
        this.row = row;
        this.column = column;
        this.maLoaiGhe = maLoaiGhe;
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

    public int getMaLoaiGhe() {
        return maLoaiGhe;
    }

    public abstract double getPrice(double basePrice);

    @Override
    public String toString() {
        String loaiGheStr = (maLoaiGhe == 2) ? "VIP" : "Thuong";
        return String.format("Ghế [Mã=%d, Phòng=%d, Hàng=%s, Cột=%d, Loại=%s]",
                seatId, roomId, row, column, loaiGheStr);
    }
}