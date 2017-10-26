package com.bookbase.app.model.interfaces;

public interface Genre {

    int getGenreId();
    String getGenreName();
    String getGenreDescription();

    void setGenreId(int id);
    void setGenreName(String genreName);
    void setGenreDescription(String genreDescription);
}
