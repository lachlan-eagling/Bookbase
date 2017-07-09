package com.bookbase.bookbase.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.bookbase.model.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertAll(Book... books);

    @Insert
    void insert(Book book);

    @Query("SELECT title FROM Book WHERE uid = :uid")
    public String getName(int uid);

    @Query("SELECT * FROM Book")
    public List<Book> getBooks();

}
