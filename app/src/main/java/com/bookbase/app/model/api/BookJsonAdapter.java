package com.bookbase.app.model.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.transition.Transition;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.utils.SaveImageHelper;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
        book.setCoverImage(in.getImageLink());
        return book;
    }

}
