package main.java.manager;
import java.sql.*;
import main.java.dao.*;
import java.util.Scanner;

public class MovieManager {
    public static void showAllMovies() {
        String sql = """
            SELECT p.MaPhim, p.TenPhim, tl.TenTheLoai, p.ThoiLuong, dt.TenDoTuoi, p.GiaVeCoBan
            FROM Phim p
            LEFT JOIN TheLoai tl ON p.MaTheLoai = tl.MaTheLoai
            LEFT JOIN DoTuoi dt ON p.MaDoTuoi = dt.MaDoTuoi
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nDANH SÁCH PHIM:");
            while (rs.next()) {
                System.out.printf("Mã: %d | Tên: %s | Thể loại: %s | Thời lượng: %d phút | Tuổi: %s | Giá: %.0f VND\n",
                        rs.getInt("MaPhim"), rs.getString("TenPhim"),
                        rs.getString("TenTheLoai"), rs.getInt("ThoiLuong"),
                        rs.getString("TenDoTuoi"), rs.getDouble("GiaVeCoBan"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn phim: " + e.getMessage());
        }
    }

    public static void searchByGenre(Scanner sc) {
        System.out.print("Nhập thể loại cần tìm: ");
        String genre = sc.nextLine();

        String sql = """
            SELECT p.TenPhim, tl.TenTheLoai, p.ThoiLuong, p.GiaVeCoBan
            FROM Phim p
            LEFT JOIN TheLoai tl ON p.MaTheLoai = tl.MaTheLoai
            WHERE tl.TenTheLoai LIKE ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + genre + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%s (%s) - %d phút - Giá %.0f VND\n",
                        rs.getString("TenPhim"), rs.getString("TenTheLoai"),
                        rs.getInt("ThoiLuong"), rs.getDouble("GiaVeCoBan"));
            }
            if (!found) System.out.println("Không tìm thấy phim nào thuộc thể loại này.");

        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn: " + e.getMessage());
        }
    }
}