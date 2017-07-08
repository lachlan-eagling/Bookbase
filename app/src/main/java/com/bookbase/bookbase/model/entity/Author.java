package com.bookbase.bookbase.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Author {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name_first")
    private String firstName;

    @ColumnInfo(name = "name_family")
    private String lastName;

    @Ignore
    private Author(){
        // Privatise default constructor to force initialisation of required fields.
    }

    public Author(String firstName, String lastName){
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUid(){ return uid; }
    public String getFirstName(){ return firstName; }
    public String getLastName() { return lastName; }

    public  void setUid(int uid){
        this.uid = uid;
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
