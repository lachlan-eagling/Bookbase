package com.bookbase.app.addBook;

import com.bookbase.app.model.entity.Book;
import java.util.List;

/**
 * Defines behavioural contract between the Presenter and View.
 * */
interface AddBookViewInterface {

    /**
     * Book has been update/inserted.
     * */
    void onBookSave();

    /**
     * An error occurred saving book to database.
     * @param error Throwable object returned from repository in csae of error on insert/update.
     * */
    void onBookSaveError(Throwable error);

    /**
     * Book title is missing or invalid.
     * */
    void titleInvalid();

    /**
     * Author is either missing or invalid.
     * */
    void authorInvalid();

    /**
     * Description is either missing or invalid.
     * */
    void descriptionInvalid();

    /**
     * Genre is either missing or invalid.
     * */
    void genreInvalid();

    /**
     * Sends book object de-serialised from GoogleBooksAPI back to the view.
     * */
    void sendApiResponse(Book book);

    /**
     * Notify view of error processing barcode.
     * */
    void onBarcodeError(String error);

    /**
     * Notify view that barcode is processing/API call in progress.
     * */
    void barcodeProcessing();

    /**
     * Get list of author names for author auto-fill fields.
     * */
    void setupAuthorAutofill(List<String> authorNames);

    /**
     * Get list of genre names for author auto-fill fields.
     * */
    void setupGenreAutofill(List<String> genreNames);

}
