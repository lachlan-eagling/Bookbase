package com.bookbase.app.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Author")
public class Author {

    @PrimaryKey(autoGenerate = true)
    private int authorId;

    @ColumnInfo(name = "name_first")
    private String firstName;


    public Author(){
    }

    public Author(String firstName){
        this.firstName = firstName;
    }

    public int getAuthorId(){ return authorId; }
    public String getFirstName(){ return firstName; }

    public void setAuthorId(int authorId){
        this.authorId = authorId;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return firstName;
    }
}
