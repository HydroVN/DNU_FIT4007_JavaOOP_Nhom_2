package main.java.manager;
import main.java.dao.*;
import java.sql.*;
import java.util.Scanner;

public class ShowManager {
    public static void showShowsByDate(Scanner sc) {
        System.out.print("Nhập ngày (yyyy-mm-dd): ");
        String date = sc.nextLine();

        String sql = """
            SELECT s.MaSuat, p.TenPhim, pc.TenPhong, s.GioBatDau, s.GioKetThuc
            FROM SuatChieu s
            JOIN Phim p ON s.MaPhim = p.MaPhim
            JOIN PhongChieu pc ON s.MaPhong = pc.MaPhong
            WHERE NgayChieu = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\nSUẤT CHIẾU NGÀY " + date + ":");
            while (rs.next()) {
                System.out.printf("Mã suất: %d | Phim: %s | Phòng: %s | %s - %s\n",
                        rs.getInt("MaSuat"), rs.getString("TenPhim"),
                        rs.getString("TenPhong"), rs.getString("GioBatDau"), rs.getString("GioKetThuc"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi suất chiếu: " + e.getMessage());
        }
    }
}

