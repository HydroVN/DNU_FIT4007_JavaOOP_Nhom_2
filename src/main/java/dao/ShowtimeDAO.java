package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Showtime;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeDAO {

    public List<Showtime> getAllShowtimes() throws DatabaseException {
        List<Showtime> showtimes = new ArrayList<>();
        String sql = "SELECT * FROM SuatChieu";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int maSuat = rs.getInt("MaSuat");
                int maPhim = rs.getInt("MaPhim");
                int maPhong = rs.getInt("MaPhong");

                Date ngayChieu = rs.getDate("NgayChieu");
                Time gioBatDau = rs.getTime("GioBatDau");
                Time gioKetThuc = rs.getTime("GioKetThuc");

                LocalDateTime startTime = LocalDateTime.of(ngayChieu.toLocalDate(), gioBatDau.toLocalTime());
                LocalDateTime endTime = LocalDateTime.of(ngayChieu.toLocalDate(), gioKetThuc.toLocalTime());

                Showtime showtime = new Showtime(maSuat, maPhim, maPhong, startTime, endTime);
                showtimes.add(showtime);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách suất chiếu: " + e.getMessage());
        }
        return showtimes;
    }

    public Showtime getShowtimeById(int id) throws DatabaseException {
        String sql = "SELECT * FROM SuatChieu WHERE MaSuat = ?";
        Showtime showtime = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int maPhim = rs.getInt("MaPhim");
                int maPhong = rs.getInt("MaPhong");
                Date ngayChieu = rs.getDate("NgayChieu");
                Time gioBatDau = rs.getTime("GioBatDau");
                Time gioKetThuc = rs.getTime("GioKetThuc");

                LocalDateTime startTime = LocalDateTime.of(ngayChieu.toLocalDate(), gioBatDau.toLocalTime());
                LocalDateTime endTime = LocalDateTime.of(ngayChieu.toLocalDate(), gioKetThuc.toLocalTime());

                showtime = new Showtime(id, maPhim, maPhong, startTime, endTime);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi tìm suất chiếu: " + e.getMessage());
        }
        return showtime;
    }

    public void addShowtime(Showtime show) throws DatabaseException {
        String sql = "INSERT INTO SuatChieu (MaSuat, MaPhim, MaPhong, NgayChieu, GioBatDau, GioKetThuc) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            LocalDate localDate = show.getStartTime().toLocalDate();
            LocalTime localStartTime = show.getStartTime().toLocalTime();
            LocalTime localEndTime = show.getEndTime().toLocalTime();

            ps.setInt(1, show.getShowtimeId());
            ps.setInt(2, show.getMovieId());
            ps.setInt(3, show.getRoomId());
            ps.setDate(4, java.sql.Date.valueOf(localDate));
            ps.setTime(5, java.sql.Time.valueOf(localStartTime));
            ps.setTime(6, java.sql.Time.valueOf(localEndTime));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi thêm suất chiếu: " + e.getMessage());
        }
    }

    public boolean deleteShowtime(int showId) throws DatabaseException {
        String sql = "DELETE FROM SuatChieu WHERE MaSuat = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, showId);
            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            if (e.getMessage().contains("FOREIGN KEY constraint")) {
                throw new DatabaseException("Không thể xóa suất chiếu này vì đã có vé được bán.");
            }
            throw new DatabaseException("Lỗi khi xóa suất chiếu: " + e.getMessage());
        }
    }
}