package main.java.model;

import java.time.LocalDateTime;

public class Showtime {
    private int showtimeId;
    private int movieId;
    private int roomId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;


    public Showtime(int showtimeId, int movieId, int roomId, LocalDateTime startTime, LocalDateTime endTime) {
        this.showtimeId = showtimeId;
        this.movieId = movieId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShowtimeId() { return showtimeId; }
    public void setShowtimeId(int showtimeId) { this.showtimeId = showtimeId; }

    public int getMovieId() { return movieId; }
    public void setMovieId(int movieId) { this.movieId = movieId; }

    public int getRoomId() { return roomId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public LocalDateTime getEndTime() { return endTime; }
    public void setEndTime(LocalDateTime endTime) { this.endTime = endTime; }

    @Override
    public String toString() {
        return "Showtime{" +
                "showtimeId=" + showtimeId +
                ", movieId=" + movieId +
                ", roomId=" + roomId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
