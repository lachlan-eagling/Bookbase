package com.bookbase.app.model.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Author;

@Dao
public interface AuthorDao {

    @Insert
    void insert(Author author);

    @Query("SELECT * FROM Author WHERE authorId=:id")
    Author getAuthorById(int id);
}
