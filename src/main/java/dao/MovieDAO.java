package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.exception.NotFoundException;
import main.java.model.Movie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for the "Phim" table.
 */
public class MovieDAO {

    // Add a new movie
    public void addMovie(Movie movie) throws DatabaseException {
        String sql = "INSERT INTO Phim (MaPhim, TenPhim, TheLoai, ThoiLuong, DoTuoi, GiaVeCoBan) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, movie.getMovieId());
            ps.setString(2, movie.getTitle());
            ps.setString(3, movie.getGenre());
            ps.setInt(4, movie.getDuration());
            ps.setString(5, movie.getAgeRating());
            ps.setDouble(6, movie.getBasePrice());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm phim: " + e.getMessage());
        }
    }

    // Delete a movie by ID
    public void deleteMovie(int id) throws NotFoundException, DatabaseException {
        String sql = "DELETE FROM Phim WHERE MaPhim = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();

            if (rows == 0) {
                throw new NotFoundException("Không tìm thấy phim có mã: " + id);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi xóa phim: " + e.getMessage());
        }
    }

    // Retrieve all movies
    public List<Movie> getAllMovies() throws DatabaseException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT MaPhim, TenPhim, TheLoai, ThoiLuong, DoTuoi, GiaVeCoBan FROM Phim";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Movie movie = new Movie(
                        rs.getInt("MaPhim"),
                        rs.getString("TenPhim"),
                        rs.getString("TheLoai"),
                        rs.getInt("ThoiLuong"),
                        rs.getString("DoTuoi"),
                        rs.getDouble("GiaVeCoBan")
                );
                movies.add(movie);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi truy vấn danh sách phim: " + e.getMessage());
        }

        return movies;
    }
}
