package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

    public List<Movie> getAllMovies() throws DatabaseException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM Phim";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("MaPhim"),
                        rs.getString("TenPhim"),
                        rs.getInt("ThoiLuong"),
                        rs.getDouble("GiaVeCoBan"),
                        rs.getInt("MaTheLoai"),
                        rs.getInt("MaDoTuoi")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách phim: " + e.getMessage());
        }
        return movies;
    }

    public Movie getMovieById(int id) throws DatabaseException {
        String sql = "SELECT * FROM Phim WHERE MaPhim = ?";
        Movie movie = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                movie = new Movie(
                        rs.getInt("MaPhim"),
                        rs.getString("TenPhim"),
                        rs.getInt("ThoiLuong"),
                        rs.getDouble("GiaVeCoBan"),
                        rs.getInt("MaTheLoai"),
                        rs.getInt("MaDoTuoi")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tìm phim: " + e.getMessage());
        }
        return movie;
    }

    public void addMovie(Movie m) throws DatabaseException {
        String sql = "INSERT INTO Phim (MaPhim, TenPhim, ThoiLuong, GiaVeCoBan, MaTheLoai, MaDoTuoi) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getMovieId());
            ps.setString(2, m.getTitle());
            ps.setInt(3, m.getDuration());
            ps.setDouble(4, m.getBasePrice());
            ps.setInt(5, m.getMaTheLoai());
            ps.setInt(6, m.getMaDoTuoi());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm phim: " + e.getMessage());
        }
    }

    public boolean deleteMovie(int id) throws DatabaseException {
        String sql = "DELETE FROM Phim WHERE MaPhim = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa phim: " + e.getMessage());
        }
    }
}