package main.java.manager;

import main.java.dao.SeatDAO;
import main.java.exception.DatabaseException;
import main.java.model.Seat;
import main.java.model.StandardSeat;
import main.java.model.VipSeat;

import java.util.ArrayList;
import java.util.List;

public class SeatManager {
    private final SeatDAO seatDAO;

    public SeatManager() {
        this.seatDAO = new SeatDAO();
    }

    public List<Seat> getAllSeats() throws DatabaseException {
        List<Seat> seats = new ArrayList<>();
        seatDAO.getAllSeats().forEach(g -> {
            if (g.getMaLoaiGhe() == 2)
                seats.add(new VipSeat(g.getSeatId(), g.getRoomId(), g.getRow(), g.getColumn()));
            else
                seats.add(new StandardSeat(g.getSeatId(), g.getRoomId(), g.getRow(), g.getColumn()));
        });
        return seats;
    }

    public double calculateSeatPrice(Seat seat, double basePrice) {
        return seat.getPrice(basePrice);
    }

    public Seat findSeatById(int seatId) throws DatabaseException {
        return seatDAO.getSeatById(seatId);
    }

    public void displayAllSeats() throws DatabaseException {
        List<Seat> seats = getAllSeats();
        System.out.println("===== DANH SÁCH GHẾ =====");
        seats.forEach(System.out::println);
    }
}