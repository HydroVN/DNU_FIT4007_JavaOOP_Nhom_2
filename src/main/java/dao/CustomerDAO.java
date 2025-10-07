package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public void insertCustomer(int maKH, String hoTen, String sdt, String email, String loaiTV) {
        String sql = "INSERT INTO KhachHang VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maKH);
            ps.setString(2, hoTen);
            ps.setString(3, sdt);
            ps.setString(4, email);
            ps.setString(5, loaiTV);
            ps.executeUpdate();
            System.out.println("✅ Thêm khách hàng thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllCustomers() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getInt("MaKH") + " - " + rs.getString("HoTen"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
