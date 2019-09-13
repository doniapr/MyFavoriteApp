package com.doniapr.myfavorite;

public class Favorite {
    private int id;
    private String title;
    private float rating;
    private String poster;
    private int isMovie;

    public Favorite(int id, String title, float rating, String poster, int isMovie) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.poster = poster;
        this.isMovie = isMovie;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getIsMovie() {
        return isMovie;
    }

    public void setIsMovie(int isMovie) {
        this.isMovie = isMovie;
    }
}
