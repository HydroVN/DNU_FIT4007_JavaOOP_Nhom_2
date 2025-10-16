import main.java.dao.*;
import main.java.exception.*;
import main.java.manager.SeatManager;
import main.java.model.*;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        CustomerDAO customerDAO = new CustomerDAO();
        MovieDAO movieDAO = new MovieDAO();
        RoomDAO roomDAO = new RoomDAO();
        SeatDAO seatDAO = new SeatDAO();
        ShowtimeDAO showDAO = new ShowtimeDAO();
        TicketDAO ticketDAO = new TicketDAO();
        ReportDAO reportDAO = new ReportDAO();

        while (true) {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ RẠP CHIẾU PHIM =====");
            System.out.println("1. Thêm khách hàng");
            System.out.println("2. Xóa khách hàng");
            System.out.println("3. Xem danh sách khách hàng");
            System.out.println("4. Thêm phim mới");
            System.out.println("5. Xóa phim");
            System.out.println("6. Xem danh sách phim");
            System.out.println("7. Xem danh sách phòng chiếu");
            System.out.println("8. Xem danh sách ghế trong phòng");
            System.out.println("9. Xem danh sách suất chiếu");
            System.out.println("10. Báo cáo doanh thu theo tháng");
            System.out.println("11. Top 3 phim có doanh thu cao nhất");
            System.out.println("12. Danh sách ghế đã đặt của 1 suất chiếu");
            System.out.println("0. Thoát chương trình");
            System.out.print("Chọn chức năng: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Nhập sai định dạng, vui lòng nhập số!");
                continue;
            }

            try {
                switch (choice) {
                    case 1 -> {
                        System.out.print("Nhập mã KH: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập họ tên: ");
                        String name = sc.nextLine();
                        System.out.print("Nhập SĐT: ");
                        String phone = sc.nextLine();
                        System.out.print("Nhập email: ");
                        String email = sc.nextLine();
                        System.out.print("Nhập loại thành viên: ");
                        String type = sc.nextLine();
                        customerDAO.addCustomer(new Customer(id, name, phone, email, type));
                        System.out.println("✅ Thêm khách hàng thành công!");
                    }
                    case 2 -> {
                        System.out.print("Nhập mã KH cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        customerDAO.deleteCustomer(id);
                        System.out.println("✅ Xóa khách hàng thành công!");
                    }
                    case 3 -> {
                        List<Customer> list = customerDAO.getAllCustomers();
                        System.out.println("===== DANH SÁCH KHÁCH HÀNG =====");
                        list.forEach(System.out::println);
                    }
                    case 4 -> {
                        System.out.print("Nhập mã phim: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập tên phim: ");
                        String title = sc.nextLine();
                        System.out.print("Nhập thể loại: ");
                        String genre = sc.nextLine();
                        System.out.print("Nhập thời lượng (phút): ");
                        int duration = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập độ tuổi (P/13+/18+): ");
                        String age = sc.nextLine();
                        System.out.print("Nhập giá vé cơ bản: ");
                        double price = Double.parseDouble(sc.nextLine());
                        movieDAO.addMovie(new Movie(id, title, genre, duration, age, price));
                        System.out.println("✅ Thêm phim thành công!");
                    }
                    case 5 -> {
                        System.out.print("Nhập mã phim cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        movieDAO.deleteMovie(id);
                        System.out.println("✅ Xóa phim thành công!");
                    }
                    case 6 -> {
                        List<Movie> list = movieDAO.getAllMovies();
                        System.out.println("===== DANH SÁCH PHIM =====");
                        list.forEach(System.out::println);
                    }
                    case 7 -> {
                        SeatManager seatManager = new SeatManager();
                        seatManager.displayAllSeats();
                    }
                    case 8 -> {
                        System.out.print("Nhập mã ghế: ");
                        int seatId = sc.nextInt();
                        Seat seat = new SeatManager().findSeatById(seatId);
                        if (seat != null)
                            System.out.println("Thông tin ghế: " + seat);
                        else
                            System.out.println("Không tìm thấy ghế!");
                    }
                    case 9 -> {
                        System.out.print("Nhập giá vé cơ bản: ");
                        double base = sc.nextDouble();
                        Seat vip = new VipSeat(1, 1, "A", 5);
                        Seat standard = new StandardSeat(2, 1, "A", 2);
                        System.out.println("Giá ghế VIP: " + vip.getPrice(base));
                        System.out.println("Giá ghế Thường: " + standard.getPrice(base));
                    }

                    /*case 10 -> {
                        System.out.print("Nhập tháng cần xem (1-12): ");
                        int month = Integer.parseInt(sc.nextLine());
                        reportDAO.showMonthlyRevenue(month);
                    }
                    case 11 -> {
                        System.out.println("===== TOP 3 PHIM DOANH THU CAO NHẤT =====");
                        reportDAO.showTop3Movies();
                    }
                    case 12 -> {
                        System.out.print("Nhập mã suất chiếu: ");
                        int showId = Integer.parseInt(sc.nextLine());
                        reportDAO.showBookedSeats(showId);
                    }*/
                    case 0 -> {
                        System.out.println("Thoát chương trình. Hẹn gặp lại!");
                        return;
                    }
                    default -> System.out.println("⚠Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.err.println("⚠️ Nhập sai định dạng số!");
            /*} catch (DatabaseException | NotFoundException e) {
                System.err.println("❌ Lỗi: " + e.getMessage());*/
            } catch (Exception e) {
                System.err.println("❗ Lỗi không xác định: " + e.getMessage());
            }
        }
    }
}
