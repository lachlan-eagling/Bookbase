package com.bookbase.bookbase.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.bookbase.bookbase.utils.Converters;
import com.bookbase.bookbase.model.dao.BookDao;
import com.bookbase.bookbase.model.entity.Author;
import com.bookbase.bookbase.model.entity.Book;

@Database(entities = {Book.class, Author.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase{

    public abstract BookDao bookDao();

}
