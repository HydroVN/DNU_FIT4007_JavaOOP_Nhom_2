package main.java.manager;
import main.java.dao.*;
import java.sql.*;
import java.util.Scanner;

public class InvoiceManager {
    public static void showAllInvoices() {
        String sql = """
            SELECT hd.MaHD, kh.HoTen, hd.NgayLap, SUM(v.GiaVe) AS TongTien
            FROM HoaDon hd
            JOIN KhachHang kh ON hd.MaKH = kh.MaKH
            LEFT JOIN ChiTietHoaDon cthd ON hd.MaHD = cthd.MaHD
            LEFT JOIN Ve v ON cthd.MaVe = v.MaVe
            GROUP BY hd.MaHD, kh.HoTen, hd.NgayLap
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nDANH SÁCH HÓA ĐƠN:");
            while (rs.next()) {
                System.out.printf("Mã HD: %d | KH: %s | Ngày: %s | Tổng: %.0f VND\n",
                        rs.getInt("MaHD"), rs.getString("HoTen"),
                        rs.getString("NgayLap"), rs.getDouble("TongTien"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi hóa đơn: " + e.getMessage());
        }
    }

    public static void revenueByDate(Scanner sc) {
        System.out.print("Nhập ngày (yyyy-mm-dd): ");
        String date = sc.nextLine();

        String sql = """
            SELECT SUM(v.GiaVe) AS DoanhThu 
            FROM HoaDon h
            JOIN ChiTietHoaDon cthd ON h.MaHD = cthd.MaHD
            JOIN Ve v ON cthd.MaVe = v.MaVe
            WHERE CONVERT(DATE, h.NgayLap) = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getDouble("DoanhThu") > 0)
                System.out.printf("Doanh thu ngày %s là: %.0f VND\n", date, rs.getDouble("DoanhThu"));
            else
                System.out.println("Không có doanh thu trong ngày này.");

        } catch (SQLException e) {
            System.out.println("Lỗi doanh thu: " + e.getMessage());
        }
    }
}