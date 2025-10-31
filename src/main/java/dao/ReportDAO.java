package main.java.dao;

import main.java.exception.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {

    public List<Map<String, Object>> getMovieRevenueReport() throws DatabaseException {
        // Thay vì List<MovieRevenueReport>, ta dùng List<Map<...>>
        List<Map<String, Object>> report = new ArrayList<>();

        String sql = "SELECT " +
                "  p.TenPhim, " +
                "  COUNT(v.MaVe) AS SoVeBan, " +
                "  SUM(v.GiaVe) AS TongDoanhThu " +
                "FROM Ve v " +
                "JOIN SuatChieu sc ON v.MaSuat = sc.MaSuat " +
                "JOIN Phim p ON sc.MaPhim = p.MaPhim " +
                "GROUP BY p.TenPhim " +
                "ORDER BY TongDoanhThu DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Tạo một Map để chứa kết quả của 1 hàng
                Map<String, Object> row = new HashMap<>();

                // Đưa dữ liệu vào Map với các key tương ứng
                row.put("tenPhim", rs.getString("TenPhim"));
                row.put("soVeBan", rs.getInt("SoVeBan"));
                row.put("tongDoanhThu", rs.getDouble("TongDoanhThu"));

                // Thêm Map này vào danh sách kết quả
                report.add(row);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tạo báo cáo doanh thu phim: " + e.getMessage());
        }
        return report;
    }

    public double getDailyRevenue(LocalDate date) throws DatabaseException {
        String sql = "SELECT SUM(TongTien) AS DoanhThuNgay FROM HoaDon WHERE CONVERT(date, NgayLap) = ?";
        double dailyRevenue = 0.0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                dailyRevenue = rs.getDouble("DoanhThuNgay");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy doanh thu ngày: " + e.getMessage());
        }
        return dailyRevenue;
    }
}