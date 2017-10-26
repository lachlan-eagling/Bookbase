package com.bookbase.app.model.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.AuthorImpl;

@Dao
public interface AuthorDao {

    @Query("SELECT * FROM Author WHERE authorId=:id")
    AuthorImpl getAuthorById(int id);
}
