package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Ticket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO {

    public void addTicket(Ticket ticket) throws DatabaseException {
        // Giả định bảng Ve có cột MaKH
        String sql = "INSERT INTO Ve (MaVe, MaSuat, MaGhe, MaKH, GiaVe) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ticket.getTicketId());
            ps.setInt(2, ticket.getShowtimeId());
            ps.setInt(3, ticket.getSeatId());
            // Sửa: Dùng getCustomerId() để khớp với model Ticket.java
            ps.setInt(4, ticket.getCustomerId());
            ps.setDouble(5, ticket.getPrice());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm vé: " + e.getMessage());
        }
    }

    public List<Ticket> getTicketsByShowtimeId(int showtimeId) throws DatabaseException {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM Ve WHERE MaSuat = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, showtimeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Sửa: Dùng constructor của Ticket.java (với customerId)
                Ticket ticket = new Ticket(
                        rs.getInt("MaVe"),
                        rs.getInt("MaSuat"),
                        rs.getInt("MaGhe"),
                        rs.getInt("MaKH"), // Giả định cột DB là MaKH
                        rs.getDouble("GiaVe")
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy vé theo suất chiếu: " + e.getMessage());
        }
        return tickets;
    }

    public List<Ticket> getTicketsByCustomerId(int customerId) throws DatabaseException {
        List<Ticket> tickets = new ArrayList<>();
        // Sửa: Truy vấn bằng MaKH thay vì MaHoaDon
        String sql = "SELECT * FROM Ve WHERE MaKH = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                // Sửa: Dùng constructor của Ticket.java
                Ticket ticket = new Ticket(
                        rs.getInt("MaVe"),
                        rs.getInt("MaSuat"),
                        rs.getInt("MaGhe"),
                        rs.getInt("MaKH"),
                        rs.getDouble("GiaVe")
                );
                tickets.add(ticket);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy vé theo khách hàng: " + e.getMessage());
        }
        return tickets;
    }
}