package main.java.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=CinemaDB;encrypt=false;";
    private static final String USER = "sa"; // đổi theo tài khoản SQL của bạn
    private static final String PASSWORD = "vanichikak6"; // đổi theo mật khẩu của bạn

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy JDBC Driver!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Lỗi khi kết nối SQL Server!");
            e.printStackTrace();
        }
        return null;
    }
}
