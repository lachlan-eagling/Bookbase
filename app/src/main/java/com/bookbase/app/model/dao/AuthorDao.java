package com.bookbase.app.model.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Author;

import java.util.List;

@Dao
public interface AuthorDao {

    @Insert
    long insert(Author author);

    @Query("SELECT * FROM Author")
    List<Author> getAuthors();

    @Query("SELECT * FROM Author WHERE authorId=:id")
    Author getAuthorById(int id);
}
