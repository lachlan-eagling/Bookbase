package com.bookbase.app.model.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.model.interfaces.Genre;

@Entity
public class GenreImpl implements Genre{

    @PrimaryKey(autoGenerate = true)
    private int genreId;
    private String genreName;
    private String genreDescription;

    public GenreImpl(){

    }

    @Override
    public int getGenreId() {
        return genreId;
    }

    @Override
    public String getGenreName() {
        return genreName;
    }

    @Override
    public String getGenreDescription() {
        return genreDescription;
    }

    @Override
    public void setGenreId(int id) {
        this.genreId = id;
    }

    @Override
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public void setGenreDescription(String genreDescription) {
        this.genreDescription = genreDescription;
    }
}
