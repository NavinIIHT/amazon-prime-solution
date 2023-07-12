package com.yaksha.challenge.model;

public class MovieReview {
    private int movieId;
    private int userId;
    private String review;
    private int rating;

    public MovieReview(int movieId, int userId, String review, int rating) {
        this.movieId = movieId;
        this.userId = userId;
        this.review = review;
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getUserId() {
        return userId;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }
}
