import main.java.dao.CustomerDAO;
import main.java.dao.MovieDAO;
import main.java.exception.DatabaseException;
import main.java.exception.NotFoundException;
import main.java.model.Customer;
import main.java.model.Movie;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        MovieDAO movieDAO = new MovieDAO();

        while (true) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM =====");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Xóa khách hàng");
            System.out.println("3. Xem danh sách khách hàng");
            System.out.println("4. Thêm phim");
            System.out.println("5. Xóa phim");
            System.out.println("6. Xem danh sách phim");
            System.out.println("0. Thoát chương trình");
            System.out.print("Chọn chức năng: ");
            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Nhập mã KH: ");
                        int id = sc.nextInt(); sc.nextLine();
                        System.out.print("Nhập họ tên: ");
                        String name = sc.nextLine();
                        System.out.print("Nhập SĐT: ");
                        String phone = sc.nextLine();
                        System.out.print("Nhập email: ");
                        String email = sc.nextLine();
                        System.out.print("Nhập loại thành viên: ");
                        String type = sc.nextLine();

                        customerDAO.addCustomer(new Customer(id, name, phone, email, type));
                        System.out.println("Thêm khách hàng thành công!");
                    }
                    case 2 -> {
                        System.out.print("Nhập mã KH cần xóa: ");
                        int id = sc.nextInt();
                        customerDAO.deleteCustomer(id);
                        System.out.println("Xóa khách hàng thành công!");
                    }
                    case 3 -> {
                        List<Customer> list = customerDAO.getAllCustomers();
                        System.out.println("===== DANH SÁCH KHÁCH HÀNG =====");
                        list.forEach(System.out::println);
                    }
                    case 4 -> {
                        System.out.print("Nhập mã phim: ");
                        int id = sc.nextInt(); sc.nextLine();
                        System.out.print("Nhập tên phim: ");
                        String title = sc.nextLine();
                        System.out.print("Nhập thể loại: ");
                        String genre = sc.nextLine();
                        System.out.print("Nhập thời lượng (phút): ");
                        int duration = sc.nextInt(); sc.nextLine();
                        System.out.print("Nhập độ tuổi (P/13+/18+): ");
                        String age = sc.nextLine();
                        System.out.print("Nhập giá vé cơ bản: ");
                        double price = sc.nextDouble();

                        movieDAO.addMovie(new Movie(id, title, genre, duration, age, price));
                        System.out.println("Thêm phim thành công!");
                    }
                    case 5 -> {
                        System.out.print("Nhập mã phim cần xóa: ");
                        int id = sc.nextInt();
                        movieDAO.deleteMovie(id);
                        System.out.println("Xóa phim thành công!");
                    }
                    case 6 -> {
                        List<Movie> list = movieDAO.getAllMovies();
                        System.out.println("===== DANH SÁCH PHIM =====");
                        list.forEach(System.out::println);
                    }
                    case 0 -> {
                        System.out.println("Thoát chương trình. Hẹn gặp lại!");
                        return;
                    }
                    default -> System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (DatabaseException | NotFoundException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
