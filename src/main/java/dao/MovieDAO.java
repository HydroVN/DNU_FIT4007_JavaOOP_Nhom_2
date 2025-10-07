package main.java.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {
    public void insertMovie(int maPhim, String tenPhim, String theLoai, int thoiLuong, String doTuoi, double giaVeCoBan) {
        String sql = "INSERT INTO Phim VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, maPhim);
            ps.setString(2, tenPhim);
            ps.setString(3, theLoai);
            ps.setInt(4, thoiLuong);
            ps.setString(5, doTuoi);
            ps.setDouble(6, giaVeCoBan);
            ps.executeUpdate();
            System.out.println("✅ Thêm phim thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllMovies() {
        List<String> movies = new ArrayList<>();
        String sql = "SELECT * FROM Phim";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(rs.getInt("MaPhim") + " - " + rs.getString("TenPhim"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
