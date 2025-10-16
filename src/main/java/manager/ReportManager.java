package main.java.manager;

import main.java.dao.*;
import main.java.exception.*;

import java.sql.*;

public class ReportManager {

    // List booked seats for a specific showtime
    public void listBookedSeats(int showtimeId) throws DatabaseException {
        String sql = """
            SELECT G.MaGhe, G.LoaiGhe, V.TrangThai
            FROM Ve V
            JOIN Ghe G ON V.MaGhe = G.MaGhe
            WHERE V.MaSuat = ? AND V.TrangThai = N'DaThanhToan';
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeId);
            ResultSet rs = ps.executeQuery();
            System.out.println("Ghế đã đặt cho suất chiếu " + showtimeId + ":");
            while (rs.next()) {
                System.out.println("- Ghế " + rs.getInt("MaGhe") + " (" + rs.getString("LoaiGhe") + ")");
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error generating seat report");
        }
    }

    // Total tickets sold by movie
    public void totalTicketsByMovie() throws DatabaseException {
        String sql = """
            SELECT P.TenPhim, COUNT(V.MaVe) AS SoVe
            FROM Ve V
            JOIN SuatChieu S ON V.MaSuat = S.MaSuat
            JOIN Phim P ON S.MaPhim = P.MaPhim
            GROUP BY P.TenPhim;
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Tổng số vé bán ra theo phim:");
            while (rs.next()) {
                System.out.printf("- %-30s : %d vé%n", rs.getString("TenPhim"), rs.getInt("SoVe"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error generating ticket report");
        }
    }

    // Monthly revenue
    public void monthlyRevenue() throws DatabaseException {
        String sql = """
            SELECT MONTH(NgayLap) AS Thang, SUM(TongTien) AS DoanhThu
            FROM HoaDon
            GROUP BY MONTH(NgayLap)
            ORDER BY Thang;
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Doanh thu theo tháng:");
            while (rs.next()) {
                System.out.printf("Tháng %d: %.0fđ%n", rs.getInt("Thang"), rs.getDouble("DoanhThu"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error generating revenue report");
        }
    }

    // Top 3 movies by revenue
    public void top3MoviesByRevenue() throws DatabaseException {
        String sql = """
            SELECT TOP 3 P.TenPhim, SUM(V.GiaVe - (V.GiaVe * V.GiamGia / 100)) AS DoanhThu
            FROM Ve V
            JOIN SuatChieu S ON V.MaSuat = S.MaSuat
            JOIN Phim P ON S.MaPhim = P.MaPhim
            WHERE V.TrangThai = N'DaThanhToan'
            GROUP BY P.TenPhim
            ORDER BY DoanhThu DESC;
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("Top 3 phim doanh thu cao nhất:");
            while (rs.next()) {
                System.out.printf("- %-30s : %.0fđ%n", rs.getString("TenPhim"), rs.getDouble("DoanhThu"));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error generating top movie report");
        }
    }
}
