package com.yaksha.challenge;

import org.junit.jupiter.api.*;

import com.yaksha.challenge.analyzer.StreamInsightsAnalyzer;
import com.yaksha.challenge.model.Movie;
import com.yaksha.challenge.model.MovieReview;
import com.yaksha.challenge.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamInsightsAnalyzerTest {
    private static final String DB_URL = "jdbc:mysql://localhost/stream_insights";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    private StreamInsightsAnalyzer analyzer;

    @BeforeEach
    public void setup() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = conn.createStatement();
        statement.executeUpdate("TRUNCATE TABLE Movie");
        statement.executeUpdate("TRUNCATE TABLE User");
        statement.executeUpdate("TRUNCATE TABLE MovieReview");
        analyzer = new StreamInsightsAnalyzer();
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        Statement statement = conn.createStatement();
        statement.executeUpdate("TRUNCATE TABLE Movie");
        statement.executeUpdate("TRUNCATE TABLE User");
        statement.executeUpdate("TRUNCATE TABLE MovieReview");
        conn.close();
    }

    @Test
    public void testAddMovie() {
        Movie movie = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie);
        Movie fetchedMovie = analyzer.getMovieDetails(1);
        assertEquals(movie.getName(), fetchedMovie.getName());
        assertEquals(movie.getGenre(), fetchedMovie.getGenre());
        assertEquals(movie.getDirector(), fetchedMovie.getDirector());
        assertEquals(movie.getStarCast(), fetchedMovie.getStarCast());
        assertEquals(movie.getLength(), fetchedMovie.getLength());
        assertEquals(movie.getCertificate(), fetchedMovie.getCertificate());
    }

    @Test
    public void testAddUser() {
        User user = new User("User 1", 25, "Male");
        analyzer.addUser(user);
        User fetchedUser = analyzer.getUserDetails(1);
        assertEquals(user.getName(), fetchedUser.getName());
        assertEquals(user.getAge(), fetchedUser.getAge());
        assertEquals(user.getGender(), fetchedUser.getGender());
    }

    /*@Test
    public void testAddMovieReview() {
        Movie movie = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie);
        User user = new User("User 1", 25, "Male");
        analyzer.addUser(user);
        MovieReview review = new MovieReview(1, 1, "Great movie!", 5);
        analyzer.addMovieReview(review);
        MovieReview fetchedReview = analyzer.getMovieReviewsByMovieId(1).get(0);
        assertEquals(review.getMovieId(), fetchedReview.getMovieId());
        assertEquals(review.getUserId(), fetchedReview.getUserId());
        assertEquals(review.getReview(), fetchedReview.getReview());
        assertEquals(review.getRating(), fetchedReview.getRating());
    }*/

    @Test
    public void testUpdateMovie() {
        Movie movie = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie);
        Movie updatedMovie = new Movie(1, "Updated Movie", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.updateMovie(updatedMovie);
        Movie fetchedMovie = analyzer.getMovieDetails(1);
        assertEquals(updatedMovie.getName(), fetchedMovie.getName());
        assertEquals(updatedMovie.getGenre(), fetchedMovie.getGenre());
        assertEquals(updatedMovie.getDirector(), fetchedMovie.getDirector());
        assertEquals(updatedMovie.getStarCast(), fetchedMovie.getStarCast());
        assertEquals(updatedMovie.getLength(), fetchedMovie.getLength());
        assertEquals(updatedMovie.getCertificate(), fetchedMovie.getCertificate());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("User 1", 25, "Male");
        analyzer.addUser(user);
        User updatedUser = new User(1, "Updated User", 30, "Female");
        analyzer.updateUser(updatedUser);
        User fetchedUser = analyzer.getUserDetails(1);
        assertEquals(updatedUser.getName(), fetchedUser.getName());
        assertEquals(updatedUser.getAge(), fetchedUser.getAge());
        assertEquals(updatedUser.getGender(), fetchedUser.getGender());
    }

    @Test
    public void testGetMovieDetails() {
        Movie movie = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie);
        Movie fetchedMovie = analyzer.getMovieDetails(1);
        assertEquals(movie.getName(), fetchedMovie.getName());
        assertEquals(movie.getGenre(), fetchedMovie.getGenre());
        assertEquals(movie.getDirector(), fetchedMovie.getDirector());
        assertEquals(movie.getStarCast(), fetchedMovie.getStarCast());
        assertEquals(movie.getLength(), fetchedMovie.getLength());
        assertEquals(movie.getCertificate(), fetchedMovie.getCertificate());
    }

    @Test
    public void testGetUserDetails() {
        User user = new User("User 1", 25, "Male");
        analyzer.addUser(user);
        User fetchedUser = analyzer.getUserDetails(1);
        assertEquals(user.getName(), fetchedUser.getName());
        assertEquals(user.getAge(), fetchedUser.getAge());
        assertEquals(user.getGender(), fetchedUser.getGender());
    }

    @Test
    public void testDeleteMovie() {
        Movie movie = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie);
        analyzer.deleteMovie(1);
        Movie fetchedMovie = analyzer.getMovieDetails(1);
        assertEquals(null, fetchedMovie);
    }

    @Test
    public void testDeleteUser() {
        User user = new User("User 1", 25, "Male");
        analyzer.addUser(user);
        analyzer.deleteUser(1);
        User fetchedUser = analyzer.getUserDetails(1);
        assertEquals(null, fetchedUser);
    }

    @Test
    public void testGetMostWatchedMovies() {
        Movie movie1 = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie1);
        Movie movie2 = new Movie("Movie 2", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.addMovie(movie2);
        Movie movie3 = new Movie("Movie 3", "Action", "Director 3", "Actor 3, Actor 4", 130, "PG-13");
        analyzer.addMovie(movie3);
        MovieReview review1 = new MovieReview(1, 1, "Review 1", 5);
        analyzer.addMovieReview(review1);
        MovieReview review2 = new MovieReview(2, 1, "Review 2", 4);
        analyzer.addMovieReview(review2);
        MovieReview review3 = new MovieReview(1, 2, "Review 3", 3);
        analyzer.addMovieReview(review3);

        List<String> mostWatchedMovies = analyzer.getMostWatchedMovies();
        assertEquals(3, mostWatchedMovies.size());
        assertEquals("Movie 1 (2 views)", mostWatchedMovies.get(0));
        assertEquals("Movie 2 (1 views)", mostWatchedMovies.get(1));
        assertEquals("Movie 3 (0 views)", mostWatchedMovies.get(2));
    }

    @Test
    public void testSortMoviesByGenreCount() {
        Movie movie1 = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie1);
        Movie movie2 = new Movie("Movie 2", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.addMovie(movie2);
        Movie movie3 = new Movie("Movie 3", "Action", "Director 3", "Actor 3, Actor 4", 130, "PG-13");
        analyzer.addMovie(movie3);

        List<String> sortedMovies = analyzer.sortMoviesByGenreCount();
        assertEquals(2, sortedMovies.size());
        assertEquals("Action (2)", sortedMovies.get(0));
        assertEquals("Drama (1)", sortedMovies.get(1));
    }

    @Test
    public void testGetTopMoviesByAgeRange() {
        Movie movie1 = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie1);
        Movie movie2 = new Movie("Movie 2", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.addMovie(movie2);
        Movie movie3 = new Movie("Movie 3", "Action", "Director 3", "Actor 3, Actor 4", 130, "PG-13");
        analyzer.addMovie(movie3);
        User user1 = new User("User 1", 25, "Male");
        analyzer.addUser(user1);
        User user2 = new User("User 2", 30, "Female");
        analyzer.addUser(user2);
        MovieReview review1 = new MovieReview(1, 1, "Review 1", 5);
        analyzer.addMovieReview(review1);
        MovieReview review2 = new MovieReview(2, 1, "Review 2", 4);
        analyzer.addMovieReview(review2);
        MovieReview review3 = new MovieReview(3, 1, "Review 3", 3);
        analyzer.addMovieReview(review3);
        MovieReview review4 = new MovieReview(1, 2, "Review 4", 4);
        analyzer.addMovieReview(review4);
        MovieReview review5 = new MovieReview(2, 2, "Review 5", 5);
        analyzer.addMovieReview(review5);
        MovieReview review6 = new MovieReview(3, 2, "Review 6", 4);
        analyzer.addMovieReview(review6);

        List<String> topMoviesByAge = analyzer.getTopMoviesByAgeRange(25, 30);
        assertEquals(2, topMoviesByAge.size());
        assertEquals("Movie 2 (2 views)", topMoviesByAge.get(0));
        assertEquals("Movie 1 (1 views)", topMoviesByAge.get(1));
    }

    @Test
    public void testSearchMoviesByDirectorAndMinRating() {
        Movie movie1 = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie1);
        Movie movie2 = new Movie("Movie 2", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.addMovie(movie2);
        Movie movie3 = new Movie("Movie 3", "Action", "Director 3", "Actor 3, Actor 4", 130, "PG-13");
        analyzer.addMovie(movie3);
        MovieReview review1 = new MovieReview(1, 1, "Review 1", 5);
        analyzer.addMovieReview(review1);
        MovieReview review2 = new MovieReview(2, 1, "Review 2", 4);
        analyzer.addMovieReview(review2);
        MovieReview review3 = new MovieReview(1, 2, "Review 3", 3);
        analyzer.addMovieReview(review3);

        List<String> moviesByDirector = analyzer.searchMoviesByDirectorAndMinRating("Director 1", 4);
        assertEquals(1, moviesByDirector.size());
        assertEquals("Movie 1", moviesByDirector.get(0));
    }

    @Test
    public void testGetLowestRatedMovieForActor() {
        Movie movie1 = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie1);
        Movie movie2 = new Movie("Movie 2", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.addMovie(movie2);
        MovieReview review1 = new MovieReview(1, 1, "Review 1", 5);
        analyzer.addMovieReview(review1);
        MovieReview review2 = new MovieReview(2, 1, "Review 2", 4);
        analyzer.addMovieReview(review2);
        MovieReview review3 = new MovieReview(1, 2, "Review 3", 3);
        analyzer.addMovieReview(review3);

        String lowestRatedMovie = analyzer.getLowestRatedMovieForActor("Actor 2");
        assertEquals("Movie 1", lowestRatedMovie);
    }

    @Test
    public void testSearchMoviesByCategoryAndLength() {
        Movie movie1 = new Movie("Movie 1", "Action", "Director 1", "Actor 1, Actor 2", 120, "PG-13");
        analyzer.addMovie(movie1);
        Movie movie2 = new Movie("Movie 2", "Drama", "Director 2", "Actor 2, Actor 3", 150, "R");
        analyzer.addMovie(movie2);
        Movie movie3 = new Movie("Movie 3", "Action", "Director 3", "Actor 3, Actor 4", 130, "PG-13");
        analyzer.addMovie(movie3);

        List<String> moviesByCategoryAndLength = analyzer.searchMoviesByCategoryAndLength("Action", 130);
        assertEquals(1, moviesByCategoryAndLength.size());
        assertEquals("Movie 1", moviesByCategoryAndLength.get(0));
    }
}
