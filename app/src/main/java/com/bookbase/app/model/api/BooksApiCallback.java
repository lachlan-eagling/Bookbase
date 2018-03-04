package com.bookbase.app.model.api;

import com.bookbase.app.model.entity.Book;

import java.util.List;

public interface BooksApiCallback{
    void onError();
    void onComplete(List<Book> books);
    //void onComplete(String response);
    void inProgress();
}