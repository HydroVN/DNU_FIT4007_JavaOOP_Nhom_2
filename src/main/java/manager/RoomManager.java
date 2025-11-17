package main.java.manager;
import java.sql.*;
import main.java.dao.*;

public class RoomManager {
    public static void showAllRooms() {
        String sql = "SELECT p.MaPhong, p.TenPhong, COUNT(g.MaGhe) AS SoLuongGhe " +
                "FROM PhongChieu p " +
                "LEFT JOIN Ghe g ON p.MaPhong = g.MaPhong " +
                "GROUP BY p.MaPhong, p.TenPhong";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nDANH SÁCH PHÒNG CHIẾU:");
            while (rs.next()) {
                System.out.printf("Mã: %d | Tên: %s | Số ghế: %d\n",
                        rs.getInt("MaPhong"), rs.getString("TenPhong"), rs.getInt("SoLuongGhe"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi phòng chiếu: " + e.getMessage());
        }
    }
}