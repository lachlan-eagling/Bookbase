package com.bookbase.app.model.api;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.squareup.moshi.FromJson;

public class BookJsonAdapter {

    @FromJson Book fromJson(BookJson in){
        Book book = new Book();
//        book.setTitle(in.getTitle());
//        book.setAuthor(new Author(in.getAuthors()));
//        book.setDescription(in.getDescription());
//        book.setRating(in.getRating());
//        book.setCoverImage(in.getImage()); // TODO: Will need to download and store image locally here.
        return book;
    }

}
