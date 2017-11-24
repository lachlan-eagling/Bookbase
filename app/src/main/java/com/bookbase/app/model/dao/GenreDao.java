package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Genre;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM Genre where genreId=:id")
    Genre getGenreById(int id);
}

