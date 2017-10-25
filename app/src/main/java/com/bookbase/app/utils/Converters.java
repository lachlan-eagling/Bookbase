package com.bookbase.app.utils;

import android.arch.persistence.room.TypeConverter;

import com.bookbase.app.activities.MainActivity;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.database.DatabaseFactory;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.interfaces.Genre;
import com.bookbase.app.model.interfaces.Review;

import java.sql.Date;
import java.util.Calendar;

public class Converters {

    private static AppDatabase db = DatabaseFactory.getDb(MainActivity.getContext());

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
    public static String calendarToLong(Calendar calendar){
        // TODO: Finish implementation.
        return "";
    }

    @TypeConverter
    public static int authorAsId(Author author){
        return author.getUid();
    }

    @TypeConverter
    public static Author intToAuthor(int id){
        return db.authorDao().getAuthorById(id);
    }

    @TypeConverter
    public static int genreAsId(Genre genre){
        return genre.getGenreId();
    }

    @TypeConverter
    public static Genre intToGenre(int id){
        return db.genreDao().getAuthorById(id);
    }

    @TypeConverter
    public static int reviewAsId(Review review){
        return review.getReviewId();
    }

    @TypeConverter
    public static Review intToReview(int id){
        return db.reviewDao().getReviewById(id);
    }

}
