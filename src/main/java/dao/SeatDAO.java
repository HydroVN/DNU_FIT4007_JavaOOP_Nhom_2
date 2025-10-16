package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Seat;
import main.java.model.StandardSeat;
import main.java.model.VipSeat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    public List<Seat> getAllSeats() throws DatabaseException {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM Ghe";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int maGhe = rs.getInt("MaGhe");
                int maPhong = rs.getInt("MaPhong");
                String soHang = rs.getString("SoHang");
                int soCot = rs.getInt("SoCot");
                String loaiGhe = rs.getString("LoaiGhe");

                // ✅ Tạo đối tượng đúng loại
                Seat seat;
                if (loaiGhe.equalsIgnoreCase("VIP")) {
                    seat = new VipSeat(maGhe, maPhong, soHang, soCot);
                } else {
                    seat = new StandardSeat(maGhe, maPhong, soHang, soCot);
                }

                seats.add(seat);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách ghế: " + e.getMessage());
        }
        return seats;
    }

    public Seat getSeatById(int id) throws DatabaseException {
        String sql = "SELECT * FROM Ghe WHERE MaGhe = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int maGhe = rs.getInt("MaGhe");
                int maPhong = rs.getInt("MaPhong");
                String soHang = rs.getString("SoHang");
                int soCot = rs.getInt("SoCot");
                String loaiGhe = rs.getString("LoaiGhe");

                // ✅ Trả đúng kiểu
                if (loaiGhe.equalsIgnoreCase("VIP")) {
                    return new VipSeat(maGhe, maPhong, soHang, soCot);
                } else {
                    return new StandardSeat(maGhe, maPhong, soHang, soCot);
                }
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi truy vấn ghế: " + e.getMessage());
        }

        return null;
    }
}
