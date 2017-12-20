package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Author")
public class Author {

    @PrimaryKey(autoGenerate = true)
    private int authorId;
    private String name;


    public Author(){
    }

    public Author(String name){
        this.name = name;
    }

    public int getAuthorId(){ return authorId; }
    public String getName(){ return name; }

    public void setAuthorId(int authorId){
        this.authorId = authorId;
    }
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
