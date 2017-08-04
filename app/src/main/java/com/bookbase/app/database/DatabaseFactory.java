package com.bookbase.app.database;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseFactory{

    private static DatabaseFactory instance;

    private static AppDatabase db;

    private static void createDb(Context context){
        if(db == null){
            db = Room.databaseBuilder(context, AppDatabase.class, "bookbase-db").build();
        }
        return;
    }

    public static AppDatabase getDb(Context context){
        if(instance == null){
            createDb(context);
            instance = new DatabaseFactory();
            return db;
        }
        return db;
    }

}
