package com.bookbase.app.library.addBook;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;

import java.util.List;

interface AddBookViewInterface {

    void onBookSave();
    void onBookSaveError(Throwable error);
    void titleInvalid();
    void authorInvalid();
    void descriptionInvalid();
    void genreInvalid();
    void sendApiResponse(Book book);
    void onBarcodeError(String error);
    void barcodeProcessing();
    void setupAuthorAutofill(List<String> authorNames);
    void setupGenreAutofill(List<String> genreNames);

}
