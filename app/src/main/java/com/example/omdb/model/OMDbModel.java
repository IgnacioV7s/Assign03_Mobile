package com.example.omdb.model;

public class OMDbModel {
    private String idFirebase, imdbID, title, type, urlImg, gender, director, actors, synopsis, rate, lengthMovie, year;

    public OMDbModel() {
    }
    public OMDbModel(String idFirebase, String imdbID, String actors, String director, String gender, String lengthMovie, String rate, String synopsis, String title, String type, String urlImg, String year) {
        this.idFirebase = idFirebase;
        this.imdbID = imdbID;
        this.actors = actors;
        this.director = director;
        this.gender = gender;
        this.lengthMovie = lengthMovie;
        this.rate = rate;
        this.synopsis = synopsis;
        this.title = title;
        this.type = type;
        this.urlImg = urlImg;
        this.year = year;
    }
    public String getIdFirebase(){ return idFirebase; }
    public void setIdFirebase(String idFirebase) { this.idFirebase = idFirebase; }
    public  String getImdbID() {
        return imdbID;
    }
    public void  setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }
    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getLengthMovie() {
        return lengthMovie;
    }

    public void setLengthMovie(String lengthMovie) {
        this.lengthMovie = lengthMovie;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}