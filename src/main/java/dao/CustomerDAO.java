package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO cho bảng KhachHang.
 */
public class CustomerDAO {

    public void addCustomer(Customer customer) throws DatabaseException {
        String sql = "INSERT INTO KhachHang (MaKH, HoTen, SDT, Email, LoaiThanhVien) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getFullName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getMemberType());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    public void deleteCustomer(int customerId) throws DatabaseException {
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa khách hàng: " + e.getMessage());
        }
    }

    public List<Customer> getAllCustomers() throws DatabaseException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT MaKH, HoTen, SDT, Email, LoaiThanhVien FROM KhachHang";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("MaKH");
                String name = rs.getString("HoTen");
                String phone = rs.getString("SDT");
                String email = rs.getString("Email");
                String memberType = rs.getString("LoaiThanhVien");

                Customer c = new Customer(id, name, phone, email, memberType);
                customers.add(c);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi truy vấn danh sách khách hàng: " + e.getMessage());
        }

        return customers; // ✅ Không trả về null
    }

}
