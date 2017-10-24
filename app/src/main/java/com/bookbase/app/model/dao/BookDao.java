package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.BookImpl;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertAll(BookImpl... books);

    @Insert
    void insert(BookImpl book);

    @Query("SELECT * FROM BookImpl")
    List<BookImpl> getBooks();

    @Query("DELETE FROM BookImpl")
    void deleteAll();

}
