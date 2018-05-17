package com.bookbase.app.library.addBook;

import android.graphics.Bitmap;

import com.bookbase.app.common.BasePresenter;
import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.bookbase.app.model.repository.Repository;
import com.bookbase.app.utils.BarcodeService;
import com.bookbase.app.utils.SaveImageHelper;
import com.crashlytics.android.Crashlytics;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.lang.Double.parseDouble;

class AddBookPresenter extends BasePresenter{

    private AddBookViewInterface view;
    private Repository repository;
    private SaveImageHelper imageHelper;
    private BarcodeService barcodeService;

    AddBookPresenter(AddBookViewInterface view) {
        this.view = view;
        repository = Repository.getRepository(HomeScreen.context);
        imageHelper = new SaveImageHelper(HomeScreen.context);
        barcodeService = new BarcodeService();
    }

    public void saveBook(int id, String title, String author, String description, String genre,
                         String review, String purchaseDate, String price,
                         float rating, Bitmap cover, boolean isEdit) {

        if(validateMandatoryFields(title, author, description, genre)) {
            // Save book to db.
            Book book = null;
            if(isEdit) {
                //It's an edit.
                book = repository.getBook(id);
                view.onBookSave();
            } else {
                // It's an insert.
                book = new Book();
            }

            book.setTitle(title);

            // Check if Author exists first.
            Author authorEntity = repository.getAuthorByName(author);
            if (authorEntity != null) {
                book.setAuthor(authorEntity);
            } else {
                book.setAuthor(new Author(author));
            }

            // Check if Genre exists first.
            Genre genreEntity = repository.getGenreByName(author);
            if (genreEntity != null) {
                book.setGenre(genreEntity);
            } else {
                book.setGenre(new Genre(genre));
            }

            book.setDescription(description);
            if(cover != null) {
                book.setCoverImage(imageHelper.saveImageToInternalStorage(cover, book));
            }
            book.setReview(new Review(Calendar.getInstance(), review));
            book.setPurchaseDate(parseDate(purchaseDate));
            book.setPurchasePrice(parseDouble(price));
            book.setRating(((int) rating));
            repository.insertBook(book, new AddBookCallback() {
                @Override
                public void inProgress() {

                }

                @Override
                public void onSuccess() {
                    view.onBookSave();
                }

                @Override
                public void onFailure() {
                    IOException e = new IOException("Unable to save book.");
                    Crashlytics.logException(e);
                    view.onBookSaveError(e);
                }
            });
        }
    }

    public void processBarcode(String image) {
        barcodeService.decodeBarcode(image, new AddBookPresenterInterface() {
            @Override
            public void returnBarcode(Book book) {
                view.sendApiResponse(book);
            }

            @Override
            public void onBarcodeError() {
                view.onBarcodeError("Error processing barcode!");
            }

            @Override
            public void barcodeProcessing() {
                view.barcodeProcessing();
            }
        });
    }

    private boolean validateMandatoryFields(String title, String author, String description, String genre) {
        boolean result = true;
        if(validateString(title)) {
            view.titleInvalid();
            result = false;
        }
        if(validateString(author)) {
            view.authorInvalid();
            result = false;
        }
        if(validateString(description)) {
            view.descriptionInvalid();
            result = false;
        }
        if(validateString(genre)) {
            view.genreInvalid();
            result = false;
        }
        return result;
    }

    private boolean validateString(String in) {
        return in != null && !in.isEmpty();
    }

    private Calendar parseDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYY", Locale.ENGLISH);

        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;

    }

    public void setupAutoFillFields() {
        view.setupAuthorAutofill(repository.getAuthorNames());
        view.setupGenreAutofill(repository.getGenreNames());
    }
}
