package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Invoice;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public void addInvoice(Invoice invoice) throws DatabaseException {
        String sql = "INSERT INTO HoaDon (MaHD, MaKH, NgayLap) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoice.getInvoiceId());
            ps.setInt(2, invoice.getCustomerId());
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(invoice.getCreatedDate()));
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    public void linkTicketToInvoice(int maHD, int maVe) throws DatabaseException {
        String sql = "INSERT INTO ChiTietHoaDon (MaHD, MaVe) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maHD);
            ps.setInt(2, maVe);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi liên kết vé vào hóa đơn: " + e.getMessage());
        }
    }

    public Invoice getInvoiceById(int id) throws DatabaseException {
        String sql = "SELECT * FROM HoaDon WHERE MaHD = ?";
        Invoice invoice = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int maHoaDon = rs.getInt("MaHD");
                int maKH = rs.getInt("MaKH");
                LocalDateTime ngayLap = rs.getTimestamp("NgayLap").toLocalDateTime();
                invoice = new Invoice(maHoaDon, maKH, ngayLap, null);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tìm hóa đơn: " + e.getMessage());
        }
        return invoice;
    }

    public List<Invoice> getInvoicesByCustomerId(int customerId) throws DatabaseException {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE MaKH = ? ORDER BY NgayLap DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                invoices.add(new Invoice(
                        rs.getInt("MaHD"),
                        rs.getInt("MaKH"),
                        rs.getTimestamp("NgayLap").toLocalDateTime(),
                        null
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy hóa đơn của khách hàng: " + e.getMessage());
        }
        return invoices;
    }
    public void deleteInvoice(int invoiceId) throws DatabaseException {
        Connection conn = null;
        String sqlGetTickets = "SELECT MaVe FROM ChiTietHoaDon WHERE MaHD = ?";
        String sqlDeleteDetails = "DELETE FROM ChiTietHoaDon WHERE MaHD = ?";
        String sqlDeleteTickets = "DELETE FROM Ve WHERE MaVe = ?";
        String sqlDeleteInvoice = "DELETE FROM HoaDon WHERE MaHD = ?";

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Bắt đầu giao dịch (Transaction)

            // BƯỚC 1: Lấy danh sách Mã Vé để chuẩn bị xóa
            List<Integer> ticketIds = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement(sqlGetTickets)) {
                ps.setInt(1, invoiceId);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    ticketIds.add(rs.getInt("MaVe"));
                }
            }

            // BƯỚC 2: Xóa liên kết trong ChiTietHoaDon trước
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteDetails)) {
                ps.setInt(1, invoiceId);
                ps.executeUpdate();
            }

            // BƯỚC 3: Xóa từng Vé trong bảng Ve (QUAN TRỌNG: Để giải phóng ghế)
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteTickets)) {
                for (int maVe : ticketIds) {
                    ps.setInt(1, maVe);
                    ps.executeUpdate();
                }
            }

            // BƯỚC 4: Cuối cùng xóa Hóa Đơn
            try (PreparedStatement ps = conn.prepareStatement(sqlDeleteInvoice)) {
                ps.setInt(1, invoiceId);
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    throw new SQLException("Không tìm thấy hóa đơn có mã: " + invoiceId);
                }
            }

            conn.commit(); // Xác nhận lưu thay đổi
            // System.out.println("Đã xóa Hóa đơn " + invoiceId + " và " + ticketIds.size() + " vé đi kèm.");

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Nếu lỗi thì hoàn tác lại tất cả
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new DatabaseException("Lỗi khi xóa hóa đơn và vé: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}