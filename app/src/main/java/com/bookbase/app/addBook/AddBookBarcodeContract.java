package com.bookbase.app.addBook;

import com.bookbase.app.model.entity.Book;

/**
 * Defines contract between AddBookPresenter and BarcodeService.
 * */
public interface AddBookBarcodeContract {

    /**
     * Passes Book object to presenter from Book API.
     */
    void returnBook(Book book);

    /**
     * Notify presenter that an error occurred decoding barcode, or retrieving book from API.
     * */
    void onBarcodeError();

    /**
     * Notify presenter that API call is in progress.
     * */
    void barcodeProcessing();
}
