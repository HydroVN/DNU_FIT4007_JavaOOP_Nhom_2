package main.java.model;

public class Ticket {
    private int ticketId;
    private int showtimeId;
    private int seatId;
    private int customerId;
    private double price;
    private String status;

    public Ticket(int ticketId, int showtimeId, int seatId, int customerId, double price, String status) {
        this.ticketId = ticketId;
        this.showtimeId = showtimeId;
        this.seatId = seatId;
        this.customerId = customerId;
        this.price = price;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId=" + ticketId +
                ", showtimeId=" + showtimeId +
                ", seatId=" + seatId +
                ", customerId=" + customerId +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}