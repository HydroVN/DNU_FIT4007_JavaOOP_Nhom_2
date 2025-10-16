package main.java.manager;
import java.sql.*;
import main.java.dao.*;
import java.util.Scanner;

public class MovieManager {
    public static void showAllMovies() {
        String sql = "SELECT * FROM Phim";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n🎥 DANH SÁCH PHIM:");
            while (rs.next()) {
                System.out.printf("Mã: %d | Tên: %s | Thể loại: %s | Thời lượng: %d phút | Giá: %.0f VND\n",
                        rs.getInt("MaPhim"), rs.getString("TenPhim"),
                        rs.getString("TheLoai"), rs.getInt("ThoiLuong"),
                        rs.getDouble("GiaVeCoBan"));
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi truy vấn phim: " + e.getMessage());
        }
    }

    public static void searchByGenre(Scanner sc) {
        System.out.print("Nhập thể loại cần tìm: ");
        String genre = sc.nextLine();

        String sql = "SELECT * FROM Phim WHERE TheLoai LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + genre + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("🎬 %s (%s) - %d phút - Giá %.0f VND\n",
                        rs.getString("TenPhim"), rs.getString("TheLoai"),
                        rs.getInt("ThoiLuong"), rs.getDouble("GiaVeCoBan"));
            }
            if (!found) System.out.println("⚠️ Không tìm thấy phim nào thuộc thể loại này.");

        } catch (SQLException e) {
            System.out.println("❌ Lỗi truy vấn: " + e.getMessage());
        }
    }
}
