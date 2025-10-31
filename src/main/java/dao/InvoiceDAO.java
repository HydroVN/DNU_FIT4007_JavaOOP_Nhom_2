package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Invoice;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {

    public void addInvoice(Invoice invoice) throws DatabaseException {
        String sql = "INSERT INTO HoaDon (MaHoaDon, MaKH, NgayLap, TongTien) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, invoice.getInvoiceId());
            ps.setInt(2, invoice.getCustomerId());
            // Sửa tên hàm cho đúng: getCreatedDate()
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(invoice.getCreatedDate()));
            ps.setDouble(4, invoice.getTotalAmount());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm hóa đơn: " + e.getMessage());
        }
    }

    public Invoice getInvoiceById(int id) throws DatabaseException {
        String sql = "SELECT * FROM HoaDon WHERE MaHoaDon = ?";
        Invoice invoice = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int maHoaDon = rs.getInt("MaHoaDon");
                int maKH = rs.getInt("MaKH");
                LocalDateTime ngayLap = rs.getTimestamp("NgayLap").toLocalDateTime();
                double tongTien = rs.getDouble("TongTien");

                // Dùng constructor, nhưng truyền danh sách vé là null
                // Lớp DAO không chịu trách nhiệm lấy dữ liệu liên kết
                invoice = new Invoice(maHoaDon, maKH, ngayLap, tongTien, null);
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
                        rs.getInt("MaHoaDon"),
                        rs.getInt("MaKH"),
                        rs.getTimestamp("NgayLap").toLocalDateTime(),
                        rs.getDouble("TongTien"),
                        null // Không tải danh sách vé ở đây
                ));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy hóa đơn của khách hàng: " + e.getMessage());
        }
        return invoices;
    }
}