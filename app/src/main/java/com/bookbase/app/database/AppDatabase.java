package com.bookbase.app.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.bookbase.app.model.dao.AuthorDao;
import com.bookbase.app.model.dao.BookDao;
import com.bookbase.app.model.dao.GenreDao;
import com.bookbase.app.model.dao.ReviewDao;
import com.bookbase.app.model.entity.AuthorImpl;
import com.bookbase.app.model.entity.BookImpl;
import com.bookbase.app.model.entity.GenreImpl;
import com.bookbase.app.model.entity.ReviewImpl;
import com.bookbase.app.utils.Converters;

@Database(entities = {BookImpl.class, AuthorImpl.class, GenreImpl.class, ReviewImpl.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase{

    private static AppDatabase db;

    public static AppDatabase getDatabase(Context context){

        if(db == null){
            db = Room.databaseBuilder(context, AppDatabase.class, "bookbase-db").build();
            return db;
        }

        return db;
    }

    public abstract BookDao bookDao();
    public abstract AuthorDao authorDao();
    public abstract GenreDao genreDao();
    public abstract ReviewDao reviewDao();

}
