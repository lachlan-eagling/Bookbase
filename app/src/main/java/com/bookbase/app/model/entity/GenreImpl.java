package com.bookbase.app.model.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.model.interfaces.Genre;

// TODO: Finish class implementation, have created this as a stub to get BookImpl working.

@Entity
public class GenreImpl implements Genre{

    @PrimaryKey
    private int genreId;

    public GenreImpl(){

    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
