package main.java.model;

public class VipSeat extends Seat {

    public VipSeat(int seatId, int roomId, String row, int column) {
        super(seatId, roomId, row, column, 2);
    }

    @Override
    public double getPrice(double basePrice) {
        return basePrice + 25000;
    }
}