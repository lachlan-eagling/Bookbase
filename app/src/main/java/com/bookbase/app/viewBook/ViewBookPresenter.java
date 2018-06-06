package com.bookbase.app.viewBook;

import com.bookbase.app.common.BasePresenter;
import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.repository.Repository;

public class ViewBookPresenter extends BasePresenter {

    private ViewBookViewInterface view;
    private Book book;
    private Repository repository;

    ViewBookPresenter(ViewBookViewInterface view) {
        this.view = view;
        repository = Repository.getRepository(HomeScreen.context);
    }

    public void deleteBook() {
        repository.deleteBook(book);
        view.closeScreen();
    }

    public void updateBook() {
        book = repository.getBook(book.getBookId());
        view.populateDetails(book);
    }

    public void setBook(int bookId) {
        this.book = repository.getBook(bookId);
    }

    public void startEditActivity() {
        view.editBook(book);
    }
}
