package main.java.manager;

import main.java.dao.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;
import main.java.dao.InvoiceDAO;
import main.java.dao.MovieDAO;
import main.java.dao.SeatDAO;
import main.java.dao.ShowtimeDAO;
import main.java.dao.TicketDAO;
import main.java.model.Invoice;
import main.java.model.Movie;
import main.java.model.Seat;
import main.java.model.Showtime;
import main.java.model.Ticket;
import main.java.exception.DatabaseException;
import main.java.exception.SeatAlreadyBookedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {

    public static void showAllInvoices() {
        String sql = "SELECT h.MaHD, h.NgayLap, k.HoTen, SUM(v.GiaVe) AS TongTien " +
                "FROM HoaDon h " +
                "JOIN KhachHang k ON h.MaKH = k.MaKH " +
                "LEFT JOIN ChiTietHoaDon cthd ON h.MaHD = cthd.MaHD " +
                "LEFT JOIN Ve v ON cthd.MaVe = v.MaVe " +
                "GROUP BY h.MaHD, h.NgayLap, k.HoTen " +
                "ORDER BY h.NgayLap DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nDANH SÁCH HÓA ĐƠN:");
            boolean found = false;
            while (rs.next()) {
                found = true;
                String thoiGian = rs.getTimestamp("NgayLap").toLocalDateTime()
                        .toString().replace("T", " ");

                System.out.printf(" [HD%03d] - %s - %s - %.0f VND\n",
                        rs.getInt("MaHD"),
                        thoiGian,
                        rs.getString("HoTen"),
                        rs.getDouble("TongTien"));
            }
            if (!found) {
                System.out.println("Chưa có hóa đơn nào trong hệ thống.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
    }

    public static void searchInvoicesByCustomer(Scanner sc) {
        System.out.print("Nhập SĐT khách hàng cần tìm hóa đơn: ");
        String phone = sc.nextLine();

        String sql = "SELECT h.MaHD, h.NgayLap, SUM(v.GiaVe) AS TongTien " +
                "FROM HoaDon h " +
                "JOIN KhachHang k ON h.MaKH = k.MaKH " +
                "LEFT JOIN ChiTietHoaDon cthd ON h.MaHD = cthd.MaHD " +
                "LEFT JOIN Ve v ON cthd.MaVe = v.MaVe " +
                "WHERE k.SDT = ? " +
                "GROUP BY h.MaHD, h.NgayLap " +
                "ORDER BY h.NgayLap DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\nKết quả hóa đơn cho SĐT: " + phone);
            while (rs.next()) {
                found = true;
                String thoiGian = rs.getTimestamp("NgayLap").toLocalDateTime()
                        .toString().replace("T", " ");

                System.out.printf(" [HD%03d] - %s - %.0f VND\n",
                        rs.getInt("MaHD"),
                        thoiGian,
                        rs.getDouble("TongTien"));
            }
            if (!found) {
                System.out.println("Không tìm thấy hóa đơn nào cho khách hàng này.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm hóa đơn của khách hàng: " + e.getMessage());
        }
    }

    public static void createBooking(int invoiceId, int customerId, int showtimeId,
                                     List<Integer> seatIds, List<Integer> ticketIds)
            throws DatabaseException, SeatAlreadyBookedException, Exception {

        ShowtimeDAO showtimeDAO = new ShowtimeDAO();
        MovieDAO movieDAO = new MovieDAO();
        SeatDAO seatDAO = new SeatDAO();
        TicketDAO ticketDAO = new TicketDAO();
        InvoiceDAO invoiceDAO = new InvoiceDAO();

        Showtime showtime = showtimeDAO.getShowtimeById(showtimeId);
        if (showtime == null) {
            throw new Exception("Không tìm thấy suất chiếu " + showtimeId);
        }

        List<Ticket> existingTickets = ticketDAO.getTicketsByShowtimeId(showtimeId);
        for (int seatId : seatIds) {
            for (Ticket existingTicket : existingTickets) {
                if (existingTicket.getSeatId() == seatId &&
                        existingTicket.getStatus().equalsIgnoreCase("DaThanhToan")) {
                    throw new SeatAlreadyBookedException("Lỗi: Ghế " + seatId + " đã có người đặt và thanh toán.", null);
                }
            }
        }

        Movie movie = movieDAO.getMovieById(showtime.getMovieId());
        if (movie == null) {
            throw new Exception("Không tìm thấy phim " + showtime.getMovieId());
        }
        double basePrice = movie.getBasePrice();

        double totalAmount = 0.0;
        List<Ticket> ticketsToCreate = new ArrayList<>();

        for (int i = 0; i < seatIds.size(); i++) {
            int seatId = seatIds.get(i);
            int ticketId = ticketIds.get(i);

            Seat seat = seatDAO.getSeatById(seatId);
            if (seat == null) {
                throw new Exception("Không tìm thấy ghế " + seatId);
            }

            if (seat.getRoomId() != showtime.getRoomId()) {
                throw new Exception("Lỗi Logic: Ghế " + seatId + " (Phòng " + seat.getRoomId() + ") không thuộc phòng của suất chiếu (Phòng " + showtime.getRoomId() + ").");
            }

            double finalPrice = seat.getPrice(basePrice);
            totalAmount += finalPrice;
            Ticket newTicket = new Ticket(ticketId, showtimeId, seatId, customerId, finalPrice, "DaThanhToan");
            ticketsToCreate.add(newTicket);
        }

        Invoice newInvoice = new Invoice(invoiceId, customerId, LocalDateTime.now(), ticketsToCreate);

        invoiceDAO.addInvoice(newInvoice);

        for (Ticket ticket : ticketsToCreate) {
            ticketDAO.addTicket(ticket);
            invoiceDAO.linkTicketToInvoice(newInvoice.getInvoiceId(), ticket.getTicketId());
        }

        System.out.println("Tạo hóa đơn [HD" + invoiceId + "] thành công! Tổng tiền: " + totalAmount + " VND");
    }
}