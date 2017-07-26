package com.bookbase.bookbase.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseFactory{

    private static DatabaseFactory instance;

    private static AppDatabase db;

    public static DatabaseFactory getInstance(Context context){
        if(instance == null){
            createDb(context);
            instance = new DatabaseFactory();
        }
        return instance;
    }

    private static void createDb(Context context){
        if(db == null){
            db = Room.databaseBuilder(context, AppDatabase.class, "bookbase-db").allowMainThreadQueries().build();
        }
        return;
    }

    public static AppDatabase getDb(){
        return db;
    }

}
