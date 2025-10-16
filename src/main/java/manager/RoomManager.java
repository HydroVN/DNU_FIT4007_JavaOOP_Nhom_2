package main.java.manager;
import java.sql.*;
import main.java.dao.*;
public class RoomManager {
    public static void showAllRooms() {
        String sql = "SELECT * FROM PhongChieu";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n🏢 DANH SÁCH PHÒNG CHIẾU:");
            while (rs.next()) {
                System.out.printf("Mã: %d | Tên: %s | Số ghế: %d\n",
                        rs.getInt("MaPhong"), rs.getString("TenPhong"), rs.getInt("SoGhe"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi phòng chiếu: " + e.getMessage());
        }
    }
}
