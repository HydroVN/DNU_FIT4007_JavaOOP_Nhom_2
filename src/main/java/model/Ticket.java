package main.java.model;

public class Ticket {
    private int ticketId;
    private int showtimeId;
    private int seatId;
    private int customerId;
    private double price;

    public Ticket(int ticketId, int showtimeId, int seatId, int customerId, double price) {
        this.ticketId = ticketId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.price = price;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getShowtimeId() {
        return showtimeId;
    }

    public int getSeatId() {
        return seatId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", showtimeId=" + showtimeId +
                ", seatId=" + seatId +
                ", customerId=" + customerId +
                ", price=" + price +
                '}';
    }
}
