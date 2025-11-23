package main.java.dao;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DatabaseConnection {
    private static Properties properties = new Properties();
    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("main/java/resources/db.properties")) {
            if (input == null) {
                System.out.println("Xin lỗi, không tìm thấy file db.properties");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Lấy thông tin từ properties
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.username");
            String pass = properties.getProperty("db.password");
            // Nếu không đọc được file thì dùng giá trị mặc định (fallback)
            if (url == null) url = "jdbc:sqlserver://localhost:1433;databaseName=CinemaDB;encrypt=true;trustServerCertificate=true";
            if (user == null) user = "sa";
            if (pass == null) pass = "123456"; // Sửa password của bạn ở đây nếu cần test nhanh
            return DriverManager.getConnection(url, user, pass);
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