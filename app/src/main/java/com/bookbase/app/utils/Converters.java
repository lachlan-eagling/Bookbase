package com.bookbase.app.utils;

import android.arch.persistence.room.TypeConverter;

import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.crashlytics.android.Crashlytics;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Converters {

    private static final AppDatabase db = AppDatabase.getDatabase(HomeScreen.context);


    @TypeConverter
    public static Calendar toCalendar(String date){
        final Calendar calendar = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
        try {
            calendar.setTime(df.parse(date));
        } catch (ParseException e) {
            Crashlytics.logException(e);
        }
        return calendar;
    }

    @TypeConverter
    public static String calendarToString(Calendar calendar){
        if(calendar == null) { calendar = Calendar.getInstance(); }
        final DateFormat df = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
        return df.format(calendar.getTime());
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
