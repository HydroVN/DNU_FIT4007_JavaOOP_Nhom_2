package main.java.manager;
import main.java.dao.*;
import java.sql.*;
import java.util.Scanner;

public class CustomerManager {
    public static void showAllCustomers() {
        String sql = """
            SELECT kh.MaKH, kh.HoTen, ltv.TenLoaiTV, kh.SDT
            FROM KhachHang kh
            JOIN LoaiThanhVien ltv ON kh.MaLoaiTV = ltv.MaLoaiTV
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nDANH SÁCH KHÁCH HÀNG:");
            while (rs.next()) {
                System.out.printf("%d - %s (%s) - %s\n",
                        rs.getInt("MaKH"), rs.getString("HoTen"),
                        rs.getString("TenLoaiTV"), rs.getString("SDT"));
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khách hàng: " + e.getMessage());
        }
    }

    public static void searchCustomer(Scanner sc) {
        System.out.print("Nhập tên hoặc loại thành viên: ");
        String key = sc.nextLine();

        String sql = """
            SELECT kh.HoTen, kh.SDT, ltv.TenLoaiTV
            FROM KhachHang kh
            JOIN LoaiThanhVien ltv ON kh.MaLoaiTV = ltv.MaLoaiTV
            WHERE kh.HoTen LIKE ? OR ltv.TenLoaiTV LIKE ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + key + "%");
            pstmt.setString(2, "%" + key + "%");
            ResultSet rs = pstmt.executeQuery();

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%s - %s - %s\n",
                        rs.getString("HoTen"), rs.getString("SDT"), rs.getString("TenLoaiTV"));
            }
            if (!found) System.out.println("Không tìm thấy khách hàng nào phù hợp.");

        } catch (SQLException e) {
            System.out.println("Lỗi tìm khách hàng: " + e.getMessage());
        }
    }
}