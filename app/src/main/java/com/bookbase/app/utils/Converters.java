package com.bookbase.app.utils;

import android.arch.persistence.room.TypeConverter;

import com.bookbase.app.model.entity.Author;

import java.sql.Date;

public class Converters {

    @TypeConverter
    public static int authorToInt(Author author){
        return -1;
    }

    @TypeConverter
    public static Long dateToLong(Date date){
        return date == null ? null : date.getTime();
    }

}
