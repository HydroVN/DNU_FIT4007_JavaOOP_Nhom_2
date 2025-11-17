package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Customer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomers() throws DatabaseException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("MaKH"),
                        rs.getString("HoTen"),
                        rs.getString("SDT"),
                        rs.getString("Email"),
                        rs.getInt("MaLoaiTV")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
        }
        return customers;
    }

    public void addCustomer(Customer c) throws DatabaseException {
        String sql = "INSERT INTO KhachHang (MaKH, HoTen, SDT, Email, MaLoaiTV) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, c.getCustomerId());
            ps.setString(2, c.getFullName());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getEmail());
            ps.setInt(5, c.getMaLoaiTV());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm khách hàng: " + e.getMessage());
        }
    }

    public boolean deleteCustomer(int id) throws DatabaseException {
        String sql = "DELETE FROM KhachHang WHERE MaKH = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa khách hàng: " + e.getMessage());
        }
    }
}