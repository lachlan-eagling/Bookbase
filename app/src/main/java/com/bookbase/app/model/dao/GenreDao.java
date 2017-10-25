package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.interfaces.Genre;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM GenreImpl where genreId=:id")
    Genre getAuthorById(int id);
}