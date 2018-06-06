package com.bookbase.app.viewBook;

import com.bookbase.app.model.entity.Book;

public interface ViewBookViewInterface {

    void closeScreen();
    void populateDetails(Book book);
    void editBook(Book book);
}
