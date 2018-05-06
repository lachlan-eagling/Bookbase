package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Genre;

import java.util.List;

@Dao
public interface GenreDao {

    @Query("SELECT * FROM Genre")
    List<Genre> getGenres();

    @Query("SELECT * FROM Genre where genreId=:id")
    Genre getGenreById(int id);

    @Query("SELECT genreName from Genre")
    List<String> getGenreNames();

    @Insert
    long insert(Genre genre);
}

