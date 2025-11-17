import main.java.dao.*;
import main.java.exception.*;
import main.java.model.*;
import main.java.manager.ReportManager;
import main.java.manager.BookingManager;
import main.java.manager.CustomerManager;
import main.java.manager.MovieManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
        ReportManager reportManager = new ReportManager();

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
            System.out.println("13. Top 3 phim có doanh thu cao nhất ");
            System.out.println("14. Danh sách ghế đã đặt của 1 suất chiếu");
            System.out.println("15. Tạo Hóa Đơn (Đặt vé)");
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
                        System.out.print("Nhập Mã Loại Thành Viên (1=Thuong, 2=SV, 3=VIP): ");
                        int typeId = Integer.parseInt(sc.nextLine());
                        customerDAO.addCustomer(new Customer(id, name, phone, email, typeId));
                        System.out.println("Thêm khách hàng thành công!");
                    }
                    case 2 -> {
                        System.out.print("Nhập mã KH cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean success = customerDAO.deleteCustomer(id);
                        if (success) {
                            System.out.println("Xóa khách hàng thành công!");
                        } else {
                            System.out.println("Xóa thất bại! Không tìm thấy khách hàng có mã: " + id);
                        }
                    }
                    case 3 -> {
                        CustomerManager.showAllCustomers();
                    }
                    case 4 -> {
                        System.out.print("Nhập mã phim: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập tên phim: ");
                        String title = sc.nextLine();
                        System.out.print("Nhập thời lượng (phút): ");
                        int duration = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập giá vé cơ bản: ");
                        double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Nhập Mã Thể Loại (1=HĐ, 2=HH...): ");
                        int maTheLoai = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Mã Độ Tuổi (1=P, 2=13+...): ");
                        int maDoTuoi = Integer.parseInt(sc.nextLine());
                        movieDAO.addMovie(new Movie(id, title, duration, price, maTheLoai, maDoTuoi));
                        System.out.println("Thêm phim thành công!");
                    }
                    case 5 -> {
                        System.out.print("Nhập mã phim cần xóa: ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean success = movieDAO.deleteMovie(id);
                        if (success) {
                            System.out.println("Xóa phim thành công!");
                        } else {
                            System.out.println("Xóa thất bại! Không tìm thấy phim có mã: " + id);
                        }
                    }
                    case 6 -> {
                        MovieManager.showAllMovies();
                    }
                    case 7 -> {
                        List<Room> list = roomDAO.getAllRooms();
                        System.out.println("===== DANH SÁCH PHÒNG CHIẾU =====");
                        for (Room room : list) {
                            System.out.println(room.getRoomName());
                        }
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
                            String tenPhim = (movie != null) ? movie.getTitle() : "Không tìm thấy phim";

                            Room room = roomDAO.getRoomById(show.getRoomId());
                            String tenPhong = (room != null) ? room.getRoomName() : "N/A";

                            LocalDateTime startTime = show.getStartTime();
                            String ngayChieu = startTime.format(DATE_FORMATTER);
                            String gioChieu = startTime.format(TIME_FORMATTER);

                            System.out.printf("Mã: %d | Phim: %s | Phòng: %s | Ngày: %s | Giờ: %s\n",
                                    show.getShowtimeId(),
                                    tenPhim,
                                    tenPhong,
                                    ngayChieu,
                                    gioChieu);
                            System.out.println("--------------");
                        }
                    }
                    case 12 -> {
                        System.out.println("===== BÁO CÁO DOANH THU THEO THÁNG =====");
                        reportManager.monthlyRevenue();
                    }
                    case 13 -> {
                        System.out.println("===== TOP 3 PHIM DOANH THU CAO NHẤT =====");
                        reportManager.top3MoviesByRevenue();
                    }
                    case 14 -> {
                        System.out.print("Nhập mã suất chiếu: ");
                        int showId = Integer.parseInt(sc.nextLine());

                        Showtime showtime = showDAO.getShowtimeById(showId);
                        if (showtime == null) {
                            System.out.println("Không tìm thấy suất chiếu có mã: " + showId);
                        } else {
                            reportManager.listBookedSeats(showId);
                        }
                    }
                    case 15 -> {
                        System.out.println("--- Tạo Hóa Đơn Mới ---");
                        System.out.print("Nhập Mã Hóa Đơn mới: ");
                        int newInvoiceId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Mã Khách Hàng (MaKH): ");
                        int customerId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập Mã Suất Chiếu (MaSuat): ");
                        int showtimeId = Integer.parseInt(sc.nextLine());

                        Showtime showtime = showDAO.getShowtimeById(showtimeId);
                        if (showtime == null) {
                            throw new Exception("Lỗi: Không tìm thấy suất chiếu " + showtimeId);
                        }
                        int roomId = showtime.getRoomId();
                        System.out.println("Suất chiếu này thuộc Phòng " + roomId + ". Mã ghế phải bắt đầu bằng số " + roomId + " (ví dụ: " + roomId + "01, " + roomId + "02...)");

                        System.out.print("Bạn muốn đặt bao nhiêu vé? ");
                        int soLuongVe = Integer.parseInt(sc.nextLine());

                        List<Integer> seatIds = new ArrayList<>();
                        List<Integer> ticketIds = new ArrayList<>();

                        for (int i = 0; i < soLuongVe; i++) {
                            System.out.println("--- Vé thứ " + (i + 1) + " ---");
                            System.out.print("Nhập Mã Vé mới: ");
                            ticketIds.add(Integer.parseInt(sc.nextLine()));

                            System.out.print("Nhập Mã Ghế (vd: " + roomId + "01): ");
                            int seatId = Integer.parseInt(sc.nextLine());

                            if (seatId / 100 != roomId) {
                                throw new Exception("Lỗi: Ghế " + seatId + " không thuộc Phòng " + roomId + "!");
                            }
                            seatIds.add(seatId);
                        }

                        BookingManager.createBooking(newInvoiceId, customerId, showtimeId, seatIds, ticketIds);
                    }
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
            } catch (SeatAlreadyBookedException e) {
                System.err.println(e.getMessage());
            } catch (DatabaseException e) {
                System.err.println("Lỗi CSDL: " + e.getMessage());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}