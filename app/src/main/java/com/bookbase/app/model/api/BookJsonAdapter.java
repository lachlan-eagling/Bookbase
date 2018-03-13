package com.bookbase.app.model.api;

import android.support.transition.Transition;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.utils.SaveImageHelper;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

public class BookJsonAdapter {

    @ToJson BookJson toJson(Book book){
        return new BookJson();
    }

    @FromJson Book fromJson(BookJson in){
        Book book = new Book();
        book.setTitle(in.getTitle());
        book.setAuthor(new Author(in.getAuthor()));
        book.setDescription(in.getDescription());
        book.setRating(0);
        book.setCoverImage(SaveImageHelper.saveImageToInternalStorage(in.getImageLink(), book));
        return book;
    }

}
