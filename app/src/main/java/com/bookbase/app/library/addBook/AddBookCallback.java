package com.bookbase.app.library.addBook;

public interface AddBookCallback {
    void inProgress();

    void onSuccess();

    void onFailure();
}