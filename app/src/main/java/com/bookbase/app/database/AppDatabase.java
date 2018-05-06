package com.bookbase.app.database;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.bookbase.app.model.dao.AuthorDao;
import com.bookbase.app.model.dao.BookDao;
import com.bookbase.app.model.dao.GenreDao;
import com.bookbase.app.model.dao.ReviewDao;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.bookbase.app.utils.Converters;

@Database(entities = {Book.class, Author.class, Genre.class, Review.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context){

        if(db == null){
            db = Room.databaseBuilder(context, AppDatabase.class, "bookbase-db").allowMainThreadQueries().build(); // Remove main thread queries.
            return db;
        }

        return db;
    }

    public abstract BookDao bookDao();
    public abstract AuthorDao authorDao();
    public abstract GenreDao genreDao();
    public abstract ReviewDao reviewDao();

}
