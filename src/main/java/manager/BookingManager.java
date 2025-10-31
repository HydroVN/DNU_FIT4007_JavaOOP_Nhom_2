package main.java.manager;

import main.java.dao.DatabaseConnection;
import java.sql.*;
import java.util.Scanner;

public class BookingManager {

    public static void showAllInvoices() {
        String sql = "SELECT h.MaHoaDon, h.NgayLap, h.TongTien, k.HoTen " +
                "FROM HoaDon h " +
                "JOIN KhachHang k ON h.MaKH = k.MaKH " +
                "ORDER BY h.NgayLap DESC"; // Sắp xếp theo ngày mới nhất

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n🧾 DANH SÁCH HÓA ĐƠN:");
            boolean found = false;
            while (rs.next()) {
                found = true;
                // Định dạng lại ngày giờ cho đẹp hơn
                String thoiGian = rs.getTimestamp("NgayLap").toLocalDateTime()
                        .toString().replace("T", " ");

                System.out.printf(" [HD%03d] - %s - %s - %.0f VND\n",
                        rs.getInt("MaHoaDon"),
                        thoiGian,
                        rs.getString("HoTen"),
                        rs.getDouble("TongTien"));
            }
            if (!found) {
                System.out.println("Chưa có hóa đơn nào trong hệ thống.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi lấy danh sách hóa đơn: " + e.getMessage());
        }
    }

    public static void searchInvoicesByCustomer(Scanner sc) {
        System.out.print("Nhập SĐT khách hàng cần tìm hóa đơn: ");
        String phone = sc.nextLine();

        String sql = "SELECT h.MaHoaDon, h.NgayLap, h.TongTien " +
                "FROM HoaDon h " +
                "JOIN KhachHang k ON h.MaKH = k.MaKH " +
                "WHERE k.SDT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, phone);
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            System.out.println("\nKết quả hóa đơn cho SĐT: " + phone);
            while (rs.next()) {
                found = true;
                String thoiGian = rs.getTimestamp("NgayLap").toLocalDateTime()
                        .toString().replace("T", " ");

                System.out.printf(" [HD%03d] - %s - %.0f VND\n",
                        rs.getInt("MaHoaDon"),
                        thoiGian,
                        rs.getDouble("TongTien"));
            }
            if (!found) {
                System.out.println("Không tìm thấy hóa đơn nào cho khách hàng này.");
            }

        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi tìm hóa đơn của khách hàng: " + e.getMessage());
        }
    }
}