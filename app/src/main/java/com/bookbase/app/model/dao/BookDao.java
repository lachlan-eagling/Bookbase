package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.bookbase.app.model.entity.Book;

import java.util.List;

@Dao
public interface BookDao {

    @Insert
    void insertAll(List<Book> books);

    @Insert
    long insert(Book book);

    @Query("SELECT * FROM Book")
    List<Book> getBooks();

    @Query("SELECT * FROM Book where bookId = :id")
    Book getSingleBook(int id);

    @Query("DELETE FROM Book")
    void deleteAll();

    @Update
    int update(Book book);

    @Delete
    void deleteBook(Book book);

}
