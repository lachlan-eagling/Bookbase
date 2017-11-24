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

    @ColumnInfo(name = "name_family")
    private String lastName;

    public Author(){
        this("","");
    }

    public Author(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAuthorId(){ return authorId; }
    public String getFirstName(){ return firstName; }
    public String getLastName() { return lastName; }

    public void setAuthorId(int authorId){
        this.authorId = authorId;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName;
    }
}
