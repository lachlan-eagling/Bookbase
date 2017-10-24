package com.bookbase.app.utils;

import android.arch.persistence.room.TypeConverter;

import com.bookbase.app.model.entity.Author;

import java.sql.Date;
import java.util.Calendar;

public class Converters {

    @TypeConverter
    public static int authorToInt(Author author){
        return -1;
    }

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


}
