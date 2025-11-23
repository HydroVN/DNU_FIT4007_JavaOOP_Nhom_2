package main.java.manager;
import main.java.dao.*;
import java.sql.*;
import main.java.exception.*;
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
    public static void deleteInvoiceProcess(Scanner sc) {
        System.out.print("Nhập Mã Hóa Đơn cần xóa: ");
        try {
            int invoiceId = Integer.parseInt(sc.nextLine());

            // Xác nhận lại cho chắc chắn
            System.out.print("Bạn có chắc muốn xóa Hóa đơn " + invoiceId + " và toàn bộ Vé đi kèm không? (y/n): ");
            String confirm = sc.nextLine();

            if (confirm.equalsIgnoreCase("y")) {
                InvoiceDAO invoiceDAO = new InvoiceDAO();
                invoiceDAO.deleteInvoice(invoiceId);
                System.out.println("Đã xóa thành công Hóa đơn " + invoiceId + " và các vé liên quan.");
                System.out.println("Bây giờ bạn có thể xóa Suất chiếu tương ứng nếu muốn.");
            } else {
                System.out.println("Đã hủy thao tác xóa.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Mã hóa đơn không hợp lệ!");
        } catch (DatabaseException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}