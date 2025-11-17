package main.java.manager;
import main.java.dao.*;
import java.sql.*;

public class TicketManager {
    public static void showPaidTickets() {
        showTicketsByStatus("DaThanhToan");
    }

    public static void showUnpaidTickets() {
        showTicketsByStatus("ChuaThanhToan");
    }

    private static void showTicketsByStatus(String status) {
        String sql = """
            SELECT v.MaVe, p.TenPhim, pc.TenPhong, g.SoHang, g.SoCot, v.GiaVe
            FROM Ve v
            JOIN SuatChieu s ON v.MaSuat = s.MaSuat
            JOIN Phim p ON s.MaPhim = p.MaPhim
            JOIN PhongChieu pc ON s.MaPhong = pc.MaPhong
            JOIN Ghe g ON v.MaGhe = g.MaGhe
            WHERE v.TrangThai = ?
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            System.out.println(status.equals("DaThanhToan") ? "\n🎟 VÉ ĐÃ BÁN:" : "\nVÉ CHƯA THANH TOÁN:");
            while (rs.next()) {
                System.out.printf("Mã vé: %d | Phim: %s | Phòng: %s | Ghế %s%d | Giá: %.0f\n",
                        rs.getInt("MaVe"), rs.getString("TenPhim"), rs.getString("TenPhong"),
                        rs.getString("SoHang"), rs.getInt("SoCot"), rs.getDouble("GiaVe"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi vé: " + e.getMessage());
        }
    }
}

