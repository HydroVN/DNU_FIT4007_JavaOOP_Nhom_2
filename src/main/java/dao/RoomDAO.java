package main.java.dao;

import main.java.exception.DatabaseException;
import main.java.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() throws DatabaseException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM PhongChieu";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(new Room(
                        rs.getInt("MaPhong"),
                        rs.getString("TenPhong"),
                        rs.getInt("SoGhe")
                ));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Lỗi khi lấy danh sách phòng: " + e.getMessage());
        }
        return rooms;
    }
}
