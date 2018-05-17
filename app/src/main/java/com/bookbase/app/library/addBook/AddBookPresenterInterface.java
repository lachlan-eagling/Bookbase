package com.bookbase.app.library.addBook;

import com.bookbase.app.model.entity.Book;

public interface AddBookPresenterInterface {

    void returnBarcode(Book book);
    void onBarcodeError();
    void barcodeProcessing();
}
