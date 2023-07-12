package com.yaksha.challenge.model;

public class Movie {
    private int id;
    private String name;
    private String genre;
    private String director;
    private String starCast;
    private int length;
    private String certificate;

    public Movie(String name, String genre, String director, String starCast, int length, String certificate) {
        this.name = name;
        this.genre = genre;
        this.director = director;
        this.starCast = starCast;
        this.length = length;
        this.certificate = certificate;
    }

    public Movie(int id, String name, String genre, String director, String starCast, int length, String certificate) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.director = director;
        this.starCast = starCast;
        this.length = length;
        this.certificate = certificate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getStarCast() {
        return starCast;
    }

    public int getLength() {
        return length;
    }

    public String getCertificate() {
        return certificate;
    }

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
                "Genre: " + genre + "\n" +
                "Director: " + director + "\n" +
                "Star Cast: " + starCast + "\n" +
                "Length: " + length + "\n" +
                "Certificate: " + certificate + "\n";
    }
}
