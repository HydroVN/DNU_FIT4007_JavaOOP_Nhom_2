package main.java.model;

public class StandardSeat extends Seat {

    public StandardSeat(int seatId, int roomId, String row, int column) {
        super(seatId, roomId, row, column, 1);
    }

    @Override
    public double getPrice(double basePrice) {
        return basePrice;
    }
}