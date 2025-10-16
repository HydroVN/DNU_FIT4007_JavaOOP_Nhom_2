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
                        rs.getString("TheLoai"),
                        rs.getInt("ThoiLuong"),
                        rs.getString("DoTuoi"),
                        rs.getDouble("GiaVeCoBan")
                ));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách phim: " + e.getMessage());
        }
        return movies;
    }

    public void addMovie(Movie m) throws DatabaseException {
        String sql = "INSERT INTO Phim VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, m.getMovieId());
            ps.setString(2, m.getTitle());
            ps.setString(3, m.getGenre());
            ps.setInt(4, m.getDuration());
            ps.setString(5, m.getAgeRating());
            ps.setDouble(6, m.getBasePrice());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm phim: " + e.getMessage());
        }
    }

    public void deleteMovie(int id) throws DatabaseException {
        String sql = "DELETE FROM Phim WHERE MaPhim = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa phim: " + e.getMessage());
        }
    }
}
