package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() throws DatabaseException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT MaPhong, TenPhong FROM PhongChieu";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong")
                ));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách phòng: " + e.getMessage());
        }
        return rooms;
    }

    public Room getRoomById(int id) throws DatabaseException {
        String sql = "SELECT MaPhong, TenPhong FROM PhongChieu WHERE MaPhong = ?";
        Room room = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                room = new Room(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong")
                );
            }
        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy phòng: " + e.getMessage());
        }
        return room;
    }
}