import main.java.dao.*;
import main.java.exception.*;
import main.java.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class Main {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

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
            System.out.println("9. Thêm suất chiếu");
            System.out.println("10. Xóa suất chiếu");
            System.out.println("11. Xem danh sách suất chiếu");
            System.out.println("12. Báo cáo doanh thu theo tháng");
            System.out.println("13. Top 3 phim có doanh thu cao nhất");
            System.out.println("14. Danh sách ghế đã đặt của 1 suất chiếu");
            System.out.println("0. Thoát chương trình");
            System.out.print("Chọn chức năng: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Nhập sai định dạng, vui lòng nhập số!");
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
                        System.out.println("Thêm khách hàng thành công!");
                    }
                    case 2 -> {
                        System.out.print("Nhập mã KH cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
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
                        System.out.println("Thêm phim thành công!");
                    }
                    case 5 -> {
                        System.out.print("Nhập mã phim cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        movieDAO.deleteMovie(id);
                        System.out.println("Xóa phim thành công!");
                    }
                    case 6 -> {
                        List<Movie> list = movieDAO.getAllMovies();
                        System.out.println("===== DANH SÁCH PHIM =====");
                        list.forEach(System.out::println);
                    }
                    case 7 -> {
                        List<Room> list = roomDAO.getAllRooms();
                        System.out.println("===== DANH SÁCH PHÒNG CHIẾU =====");
                        list.forEach(System.out::println);
                    }
                    case 8 -> {
                        System.out.print("Nhập mã phòng (roomId) cần xem: ");
                        int roomId = Integer.parseInt(sc.nextLine());
                        List<Seat> list = seatDAO.getSeatsByRoomId(roomId);
                        System.out.println("===== DANH SÁCH GHẾ PHÒNG " + roomId + " =====");
                        if (list.isEmpty()) {
                            System.out.println("Phòng này không có ghế hoặc mã phòng không tồn tại.");
                        } else {
                            list.forEach(System.out::println);
                        }
                    }
                    case 9 -> {
                        System.out.println("--- Thêm suất chiếu mới ---");
                        System.out.print("Nhập Mã Suất chiếu: ");
                        int showId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Mã Phim: ");
                        int movieId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Mã Phòng: ");
                        int roomId = Integer.parseInt(sc.nextLine());

                        System.out.print("Nhập Ngày chiếu (dd/MM/yyyy): ");
                        LocalDate date = LocalDate.parse(sc.nextLine(), DATE_FORMATTER);
                        System.out.print("Nhập Giờ bắt đầu (HH:mm): ");
                        LocalTime startTime = LocalTime.parse(sc.nextLine(), TIME_FORMATTER);
                        System.out.print("Nhập Giờ kết thúc (HH:mm): ");
                        LocalTime endTime = LocalTime.parse(sc.nextLine(), TIME_FORMATTER);

                        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
                        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

                        Showtime newShow = new Showtime(showId, movieId, roomId, startDateTime, endDateTime);
                        showDAO.addShowtime(newShow);
                        System.out.println("Thêm suất chiếu thành công!");
                    }
                    case 10 -> {
                        System.out.print("Nhập Mã Suất chiếu cần xóa: ");
                        int showId = Integer.parseInt(sc.nextLine());
                        boolean success = showDAO.deleteShowtime(showId);

                        if (success) {
                            System.out.println("Xóa suất chiếu thành công!");
                        } else {
                            System.out.println("Không tìm thấy suất chiếu có mã: " + showId);
                        }
                    }
                    case 11 -> {
                        List<Showtime> list = showDAO.getAllShowtimes();
                        System.out.println("===== DANH SÁCH SUẤT CHIẾU =====");

                        for (Showtime show : list) {
                            Movie movie = movieDAO.getMovieById(show.getMovieId());
                            String tenPhim = "Không tìm thấy phim";
                            if (movie != null) {
                                tenPhim = movie.getTitle();
                            }

                            LocalDateTime startTime = show.getStartTime();

                            String ngayChieu = startTime.format(DATE_FORMATTER);
                            String gioChieu = startTime.format(TIME_FORMATTER);

                            System.out.println("Tên phim: " + tenPhim);
                            System.out.println("Ngày: " + ngayChieu);
                            System.out.println("Thời gian: " + gioChieu);
                            System.out.println("--------------");
                        }
                    }
                    /*case 12 -> {
                        System.out.print("Nhập tháng cần xem (1-12): ");
                        int month = Integer.parseInt(sc.nextLine());
                        reportDAO.showMonthlyRevenue(month);
                    }
                    case 13 -> {
                        System.out.println("===== TOP 3 PHIM DOANH THU CAO NHẤT =====");
                        reportDAO.showTop3Movies();
                    }
                    case 14 -> {
                        System.out.print("Nhập mã suất chiếu: ");
                        int showId = Integer.parseInt(sc.nextLine());
                        reportDAO.showBookedSeats(showId);
                    }*/
                    case 0 -> {
                        System.out.println("Thoát chương trình. Hẹn gặp lại!");
                        return;
                    }
                    default -> System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Nhập sai định dạng số!");
            } catch (java.time.format.DateTimeParseException e) {
                System.err.println("Nhập sai định dạng ngày/giờ! (Vd: 08/10/2025 hoặc 14:30)");
            } catch (DatabaseException e) {
                System.err.println("Lỗi CSDL: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Lỗi không xác định: " + e.getMessage());
            }
        }
    }
}