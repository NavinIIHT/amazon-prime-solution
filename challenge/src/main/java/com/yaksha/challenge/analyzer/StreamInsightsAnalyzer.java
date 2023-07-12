package com.yaksha.challenge.analyzer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.yaksha.challenge.model.Movie;
import com.yaksha.challenge.model.MovieReview;
import com.yaksha.challenge.model.User;

public class StreamInsightsAnalyzer {
    private static final String DB_URL = "jdbc:mysql://localhost/stream_insights";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    private Connection conn;

    public StreamInsightsAnalyzer() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovie(Movie movie) {
        try {
            String sql = "INSERT INTO Movie (name, genre, director, starCast, length, certificate) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getGenre());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getStarCast());
            statement.setInt(5, movie.getLength());
            statement.setString(6, movie.getCertificate());
            statement.executeUpdate();
            System.out.println("Movie added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUser(User user) {
        try {
            String sql = "INSERT INTO User (name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.setString(3, user.getGender());
            statement.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovieReview(MovieReview review) {
        try {
            String sql = "INSERT INTO MovieReview (movieId, userId, review, rating) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, review.getMovieId());
            statement.setInt(2, review.getUserId());
            statement.setString(3, review.getReview());
            statement.setInt(4, review.getRating());
            statement.executeUpdate();
            System.out.println("Movie review added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMovie(Movie movie) {
        try {
            String sql = "UPDATE Movie SET name = ?, genre = ?, director = ?, starCast = ?, length = ?, certificate = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getGenre());
            statement.setString(3, movie.getDirector());
            statement.setString(4, movie.getStarCast());
            statement.setInt(5, movie.getLength());
            statement.setString(6, movie.getCertificate());
            statement.setInt(7, movie.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie updated successfully.");
            } else {
                System.out.println("Movie not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        try {
            String sql = "UPDATE User SET name = ?, age = ?, gender = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setInt(2, user.getAge());
            statement.setString(3, user.getGender());
            statement.setInt(4, user.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Movie getMovieDetails(int movieId) {
        try {
            String sql = "SELECT * FROM Movie WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, movieId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Movie(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("genre"),
                        resultSet.getString("director"),
                        resultSet.getString("starCast"),
                        resultSet.getInt("length"),
                        resultSet.getString("certificate")
                );
            } else {
                System.out.println("Movie not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getUserDetails(int userId) {
        try {
            String sql = "SELECT * FROM User WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getString("gender")
                );
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteMovie(int movieId) {
        try {
            String sql = "DELETE FROM Movie WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, movieId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Movie deleted successfully.");
            } else {
                System.out.println("Movie not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(int userId) {
        try {
            String sql = "DELETE FROM User WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("User not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMostWatchedMovies() {
        List<String> mostWatchedMovies = new ArrayList<>();
        try {
            String sql = "SELECT Movie.name, COUNT(MovieReview.movieId) AS watchCount " +
                    "FROM Movie LEFT JOIN MovieReview ON Movie.id = MovieReview.movieId " +
                    "GROUP BY Movie.id ORDER BY watchCount DESC";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String movieName = resultSet.getString("name");
                int watchCount = resultSet.getInt("watchCount");
                mostWatchedMovies.add(movieName + " (" + watchCount + " views)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostWatchedMovies;
    }

    public List<String> sortMoviesByGenreCount() {
        List<String> sortedMovies = new ArrayList<>();
        try {
            String sql = "SELECT genre, COUNT(id) AS genreCount FROM Movie GROUP BY genre ORDER BY genreCount DESC";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String genre = resultSet.getString("genre");
                int genreCount = resultSet.getInt("genreCount");
                sortedMovies.add(genre + " (" + genreCount + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sortedMovies;
    }

    public List<String> getTopMoviesByAgeRange(int minAge, int maxAge) {
        List<String> topMovies = new ArrayList<>();
        try {
            String sql = "SELECT Movie.name, COUNT(MovieReview.movieId) AS watchCount " +
                    "FROM MovieReview INNER JOIN User ON MovieReview.userId = User.id " +
                    "INNER JOIN Movie ON MovieReview.movieId = Movie.id " +
                    "WHERE User.age >= ? AND User.age <= ? " +
                    "GROUP BY Movie.id ORDER BY watchCount DESC LIMIT 3";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, minAge);
            statement.setInt(2, maxAge);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String movieName = resultSet.getString("name");
                int watchCount = resultSet.getInt("watchCount");
                topMovies.add(movieName + " (" + watchCount + " views)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topMovies;
    }

    public List<String> searchMoviesByDirectorAndMinRating(String director, int minRating) {
        List<String> movies = new ArrayList<>();
        try {
            String sql = "SELECT Movie.name FROM MovieReview " +
                    "INNER JOIN Movie ON MovieReview.movieId = Movie.id " +
                    "WHERE Movie.director = ? AND MovieReview.rating >= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, director);
            statement.setInt(2, minRating);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String movieName = resultSet.getString("name");
                movies.add(movieName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public String getLowestRatedMovieForActor(String actor) {
        try {
            String sql = "SELECT Movie.name FROM Movie " +
                    "INNER JOIN MovieReview ON Movie.id = MovieReview.movieId " +
                    "WHERE Movie.starCast LIKE ? " +
                    "ORDER BY MovieReview.rating ASC LIMIT 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + actor + "%");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "No movie found for the actor.";
    }

    public List<String> searchMoviesByCategoryAndLength(String category, int maxLength) {
        List<String> movies = new ArrayList<>();
        try {
            String sql = "SELECT name FROM Movie WHERE genre = ? AND length <= ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category);
            statement.setInt(2, maxLength);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String movieName = resultSet.getString("name");
                movies.add(movieName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    
    
}

