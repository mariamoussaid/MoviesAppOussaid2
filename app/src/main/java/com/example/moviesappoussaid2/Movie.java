package com.example.moviesappoussaid2;
// Movie.java

public class Movie {
    private String title;
    private String releaseDate;
    private String overview;
    private String imageUrl; // Add this field


    public Movie(String title, String releaseDate, String overview, String imageUrl) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.imageUrl = imageUrl;

    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public String getImageUrl() { return imageUrl;
    }
    // You can add setter methods if necessary
}
