package com.bookbase.bookbase.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="is_read")
    private boolean isRead;
    private int published;
    private int rating;
    private int author;
    private String description;
    private String edition;
    private String isbn;
    private String title;

    @Ignore
    private Book(){
        // Privatise default constructor to force initialisation of required fields.
    }

    @Ignore
    public Book(String title, int author){
        this.title = title;
        this.author = author;

        this.isRead = false;
        this.published = 0;
        this.rating = 0;
        this.author = -1;
        this.description = "";
        this.edition = "";
        this.isbn = "";
    }

    public Book(String title,
                int author,
                int published,
                String description,
                String edition,
                String isbn){
        this.title = title;
        this.author = author;
        this.published = published;
        this.description = description;
        this.edition = edition;
        this.isbn = isbn;

        this.isRead = false;
        this.rating = 0;
        this.author = -1;
    }



    public int getUid(){ return uid; }
    public boolean getIsRead(){ return isRead; }
    public int getPublished(){ return published; }
    public int getRating(){ return rating; }
    public int getAuthor(){ return author; }
    public String getDescription(){ return description; }
    public String getEdition(){ return edition; }
    public String getIsbn(){ return isbn; }
    public String getTitle(){ return title; }

    public void setUid(int uid){
        this.uid = uid;
    }

    public void setIsRead(boolean isRead){
        this.isRead = isRead;
    }

    public void setPublished(int published){
        this.published = published;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public void setAuthor(int author){
        this.author = author;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setEdition(String edition){
        this.edition = edition;
    }

    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    public void setTitle(String title){
        this.title = title;
    }

}
