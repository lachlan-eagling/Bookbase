package com.bookbase.app.model.api;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

@SuppressWarnings("unused")
class BookJsonAdapter {

    @ToJson BookJson toJson(Book book){
        return new BookJson();
    }

    @FromJson Book fromJson(BookJson in){
        Book book = new Book();
        book.setTitle(in.getTitle());
        book.setAuthor(new Author(in.getAuthor()));
        book.setDescription(in.getDescription());
        book.setRating(0);
        book.setCoverImage(in.getImageLink());
        return book;
    }

}
