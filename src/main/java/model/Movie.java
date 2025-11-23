package main.java.model;
public class Movie {
    private int movieId;
    private String title;
    private int duration;
    private double basePrice;
    private int maTheLoai;
    private int maDoTuoi;

    public Movie(int movieId, String title, int duration, double basePrice, int maTheLoai, int maDoTuoi) {
        this.movieId = movieId;
        this.title = title;
        this.duration = duration;
        this.basePrice = basePrice;
        this.maTheLoai = maTheLoai;
        this.maDoTuoi = maDoTuoi;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public int getMaTheLoai() {
        return maTheLoai;
    }

    public void setMaTheLoai(int maTheLoai) {
        this.maTheLoai = maTheLoai;
    }

    public int getMaDoTuoi() {
        return maDoTuoi;
    }

    public void setMaDoTuoi(int maDoTuoi) {
        this.maDoTuoi = maDoTuoi;
    }

    @Override
    public String toString() {
        return "Phim: " + title + " (ID: " + movieId + ")";
    }
}