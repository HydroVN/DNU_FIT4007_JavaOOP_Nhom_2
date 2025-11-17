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
        String sql = "INSERT INTO Ve (MaVe, MaSuat, MaGhe, GiaVe, GiamGia, TrangThai) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ticket.getTicketId());
            ps.setInt(2, ticket.getShowtimeId());
            ps.setInt(3, ticket.getSeatId());
            ps.setDouble(4, ticket.getPrice());
            ps.setDouble(5, 0);
            ps.setString(6, ticket.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm vé (TicketDAO): " + e.getMessage());
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
                Ticket ticket = new Ticket(
                        rs.getInt("MaVe"),
                        rs.getInt("MaSuat"),
                        rs.getInt("MaGhe"),
                        0,
                        rs.getDouble("GiaVe"),
                        rs.getString("TrangThai")
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
        String sql = "SELECT v.* " +
                "FROM Ve v " +
                "JOIN ChiTietHoaDon cthd ON v.MaVe = cthd.MaVe " +
                "JOIN HoaDon hd ON cthd.MaHD = hd.MaHD " +
                "WHERE hd.MaKH = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket(
                        rs.getInt("MaVe"),
                        rs.getInt("MaSuat"),
                        rs.getInt("MaGhe"),
                        customerId,
                        rs.getDouble("GiaVe"),
                        rs.getString("TrangThai")
                );
                tickets.add(ticket);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy vé theo khách hàng: " + e.getMessage());
        }
        return tickets;
    }
}