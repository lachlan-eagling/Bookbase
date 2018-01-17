package com.bookbase.app.utils;

import android.os.Bundle;

import com.bookbase.app.model.entity.Book;

public class BundleBookHelper {

    private static final String ID_KEY = "bookId";
    private static final String TITLE_KEY = "title";
    private static final String AUTHOR_KEY = "author";
    private static final String GENRE_KEY = "genre";
    private static final String COVER_IMAGE_KEY = "coverImage";
    private static final String ISBN_KEY = "isbn";
    private static final String RATING_KEY = "rating";
    private static final String REVIEW_KEY = "review";
    private static final String IS_READ_KEY = "isRead";
    private static final String PURCHASE_DATE_KEY = "purchaseDate";
    private static final String PRICE_KEY = "price";
    private static final String OWNED_KEY = "owned";

    public static Bundle bundleBook(Book book){
        Bundle bundle = new Bundle();
        bundle.putInt(ID_KEY, book.getBookId());
        bundle.putString(TITLE_KEY, book.getTitle());
        //bundle.putInt(AUTHOR_KEY, book.getAuthor());
        //bundle.putInt(GENRE_KEY, book.getGenre()) //TODO: Need to fix so can bundle Genre val.
        bundle.putString(COVER_IMAGE_KEY, book.getCoverImage());
        bundle.putString(ISBN_KEY, book.getIsbn());
        bundle.putInt(RATING_KEY, book.getRating());
        bundle.putString(REVIEW_KEY, book.getReview());
        bundle.putBoolean(IS_READ_KEY, book.getIsRead());
        bundle.putString(PURCHASE_DATE_KEY, Converters.calendarToString(book.getPurchaseDate()));
        bundle.putDouble(PRICE_KEY, book.getPurchasePrice());
        bundle.putBoolean(OWNED_KEY, book.getIsOwned());
        return bundle;
    }

    public static Book unbundleBook(Bundle bundle){
        Book book = new Book();
        book.setBookId(bundle.getInt(ID_KEY));
        book.setTitle(bundle.getString(TITLE_KEY));
        //book.setAuthor(bundle.getInt(AUTHOR_KEY));
        //book.setGenre(bundle.getInt(GENRE_KEY)); //TODO: Need to fix so can bundle Genre val.
        book.setCoverImage(bundle.getString(COVER_IMAGE_KEY));
        book.setIsbn(bundle.getString(ISBN_KEY));
        book.setRating(bundle.getInt(RATING_KEY));
        book.setReview(bundle.getString(REVIEW_KEY));
        book.setIsRead(bundle.getBoolean(IS_READ_KEY));
        book.setPurchaseDate(Converters.toCalendar(bundle.getString(PURCHASE_DATE_KEY)));
        book.setPurchasePrice(bundle.getDouble(PRICE_KEY));
        book.setIsOwned(bundle.getBoolean(OWNED_KEY));
        return book;
    }

}
