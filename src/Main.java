import main.java.dao.*;

public class Main {
    public static void main(String[] args) {
        MovieDAO movieDAO = new MovieDAO();
        CustomerDAO customerDAO = new CustomerDAO();

        // Thêm thử 1 phim
        movieDAO.insertMovie(7, "Inception", "Khoa học viễn tưởng", 148, "13+", 90000);

        // Lấy danh sách phim
        System.out.println("Danh sách phim:");
        movieDAO.getAllMovies().forEach(System.out::println);

        // Thêm thử khách hàng
        customerDAO.insertCustomer(11, "Nguyễn Minh Tâm", "0987654321", "tam@gmail.com", "VIP");

        System.out.println("Danh sách khách hàng:");
        customerDAO.getAllCustomers().forEach(System.out::println);
    }
}
