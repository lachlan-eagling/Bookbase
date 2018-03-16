package com.bookbase.app.utils;

import android.arch.persistence.room.TypeConverter;

import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;

import java.sql.Date;
import java.util.Calendar;

public class Converters {

    private static AppDatabase db = AppDatabase.getDatabase(HomeScreen.getContext());

    @TypeConverter
    public static Long dateToLong(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Calendar toCalendar(String date){
        // TODO: Complete implementation.
        return Calendar.getInstance();
    }

    @TypeConverter
    public static String calendarToString(Calendar calendar){
        // TODO: Finish implementation.
        return "";
    }

    @TypeConverter
    public static int authorAsId(Author author){
        if(db.authorDao().getAuthorById(author.getAuthorId()) != null) {
            return author.getAuthorId();
        }
        return (int) db.authorDao().insert(author);
    }

    @TypeConverter
    public static Author intToAuthor(int id){
        return db.authorDao().getAuthorById(id);
    }

    @TypeConverter
    public static int genreAsId(Genre genre){
        if(db.genreDao().getGenreById(genre.getGenreId()) != null) {
            return genre.getGenreId();
        }
        return (int) db.genreDao().insert(genre);
    }

    @TypeConverter
    public static Genre intToGenre(int id){
        return db.genreDao().getGenreById(id);
    }

    @TypeConverter
    public static int reviewAsId(Review review){ return (int) db.reviewDao().insert(review); }

    @TypeConverter
    public static Review intToReview(int id) {return db.reviewDao().getReviewById(id);}

}
