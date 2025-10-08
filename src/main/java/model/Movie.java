package main.java.model;

/**
 * Lớp đại diện cho đối tượng Phim.
 */
public class Movie {
    private int movieId;
    private String title;
    private String genre;
    private int duration;
    private String ageLimit;
    private double basePrice;

    public Movie(int movieId, String title, String genre, int duration, String ageLimit, double basePrice) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.ageLimit = ageLimit;
        this.basePrice = basePrice;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return "Phim: " + title + " (" + genre + ")";
    }

    public String getAgeRating() {
        return null;
    }
}
