package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertAll(Book... books);

    @Insert
    void insert(Book book);

    @Query("SELECT * FROM Book")
    List<Book> getBooks();

    @Query("DELETE FROM Book")
    void deleteAll();

}
