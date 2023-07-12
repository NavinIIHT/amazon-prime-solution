package com.yaksha.challenge;

import java.util.List;
import java.util.Scanner;

import com.yaksha.challenge.analyzer.StreamInsightsAnalyzer;
import com.yaksha.challenge.model.Movie;
import com.yaksha.challenge.model.MovieReview;
import com.yaksha.challenge.model.User;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main(String[] args) {
        StreamInsightsAnalyzer analyzer = new StreamInsightsAnalyzer();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("======= StreamInsights Analyzer =======");
            System.out.println("1. Add a new movie");
            System.out.println("2. Add a new user");
            System.out.println("3. Add a movie review");
            System.out.println("4. Update a movie");
            System.out.println("5. Update a user");
            System.out.println("6. Get movie details");
            System.out.println("7. Get user details");
            System.out.println("8. Delete a movie");
            System.out.println("9. Delete a user");
            System.out.println("10. Get the list of most watched movies");
            System.out.println("11. Sort movies by genre count");
            System.out.println("12. Get top movies watched by users of age between 25-30");
            System.out.println("13. Search movies with minimum rating of 4 by a director");
            System.out.println("14. Get lowest rated movie for an actor");
            System.out.println("15. Search movies with a specific category and length");
            System.out.println("0. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                case 1:
                    System.out.print("Enter movie name: ");
                    String movieName = scanner.nextLine();
                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter director: ");
                    String director = scanner.nextLine();
                    System.out.print("Enter star cast: ");
                    String starCast = scanner.nextLine();
                    System.out.print("Enter length: ");
                    int length = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter certificate: ");
                    String certificate = scanner.nextLine();
                    Movie newMovie = new Movie(movieName, genre, director, starCast, length, certificate);
                    analyzer.addMovie(newMovie);
                    break;
                case 2:
                    System.out.print("Enter user name: ");
                    String userName = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter gender: ");
                    String gender = scanner.nextLine();
                    User user = new User(userName, age, gender);
                    analyzer.addUser(user);
                    break;
                case 3:
                    System.out.print("Enter movie ID: ");
                    int movieId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter review: ");
                    String review = scanner.nextLine();
                    System.out.print("Enter rating: ");
                    int rating = scanner.nextInt();
                    scanner.nextLine();
                    MovieReview movieReview = new MovieReview(movieId, userId, review, rating);
                    analyzer.addMovieReview(movieReview);
                    break;
                case 4:
                    System.out.print("Enter movie ID: ");
                    int updateMovieId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter updated movie name: ");
                    String updatedMovieName = scanner.nextLine();
                    System.out.print("Enter updated genre: ");
                    String updatedGenre = scanner.nextLine();
                    System.out.print("Enter updated director: ");
                    String updatedDirector = scanner.nextLine();
                    System.out.print("Enter updated star cast: ");
                    String updatedStarCast = scanner.nextLine();
                    System.out.print("Enter updated length: ");
                    int updatedLength = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter updated certificate: ");
                    String updatedCertificate = scanner.nextLine();
                    Movie updatedMovie = new Movie(updateMovieId, updatedMovieName, updatedGenre, updatedDirector, updatedStarCast, updatedLength, updatedCertificate);
                    analyzer.updateMovie(updatedMovie);
                    break;
                case 5:
                    System.out.print("Enter user ID: ");
                    int updateUserid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter updated user name: ");
                    String updatedUserName = scanner.nextLine();
                    System.out.print("Enter updated age: ");
                    int updatedAge = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter updated gender: ");
                    String updatedGender = scanner.nextLine();
                    User updatedUser = new User(updateUserid, updatedUserName, updatedAge, updatedGender);
                    analyzer.updateUser(updatedUser);
                    break;
                case 6:
                    System.out.print("Enter movie ID: ");
                    int movieDetailsId = scanner.nextInt();
                    scanner.nextLine();
                    Movie movieDetails = analyzer.getMovieDetails(movieDetailsId);
                    if (movieDetails != null) {
                        System.out.println("Movie Details:");
                        System.out.println(movieDetails);
                    }
                    break;
                case 7:
                    System.out.print("Enter user ID: ");
                    int userDetailsId = scanner.nextInt();
                    scanner.nextLine();
                    User userDetails = analyzer.getUserDetails(userDetailsId);
                    if (userDetails != null) {
                        System.out.println("User Details:");
                        System.out.println(userDetails);
                    }
                    break;
                case 8:
                    System.out.print("Enter movie ID: ");
                    int deleteMovieId = scanner.nextInt();
                    scanner.nextLine();
                    analyzer.deleteMovie(deleteMovieId);
                    break;
                case 9:
                    System.out.print("Enter user ID: ");
                    int deleteUserid = scanner.nextInt();
                    scanner.nextLine();
                    analyzer.deleteUser(deleteUserid);
                    break;
                case 10:
                    List<String> mostWatchedMovies = analyzer.getMostWatchedMovies();
                    System.out.println("Most watched movies:");
                    for (String movie : mostWatchedMovies) {
                        System.out.println(movie);
                    }
                    break;
                case 11:
                    List<String> sortedMovies = analyzer.sortMoviesByGenreCount();
                    System.out.println("Movies sorted by genre count:");
                    for (String movie : sortedMovies) {
                        System.out.println(movie);
                    }
                    break;
                case 12:
                    List<String> topMoviesByAge = analyzer.getTopMoviesByAgeRange(25, 30);
                    System.out.println("Top movies watched by users aged 25-30:");
                    for (String movie : topMoviesByAge) {
                        System.out.println(movie);
                    }
                    break;
                case 13:
                    System.out.print("Enter director name: ");
                    String directorName = scanner.nextLine();
                    List<String> moviesByDirector = analyzer.searchMoviesByDirectorAndMinRating(directorName, 4);
                    System.out.println("Movies with minimum rating of 4 by " + directorName + ":");
                    for (String movie : moviesByDirector) {
                        System.out.println(movie);
                    }
                    break;
                case 14:
                    System.out.print("Enter actor name: ");
                    String actorName = scanner.nextLine();
                    String lowestRatedMovie = analyzer.getLowestRatedMovieForActor(actorName);
                    System.out.println("Lowest rated movie for " + actorName + ": " + lowestRatedMovie);
                    break;
                case 15:
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter maximum length: ");
                    int maxLength = scanner.nextInt();
                    scanner.nextLine();
                    List<String> moviesByCategoryAndLength = analyzer.searchMoviesByCategoryAndLength(category, maxLength);
                    System.out.println("Movies with category " + category + " and length under " + maxLength + " minutes:");
                    for (String movie : moviesByCategoryAndLength) {
                        System.out.println(movie);
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
	}
}
