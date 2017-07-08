package com.bookbase.bookbase.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="is_read")
    private boolean isRead; //dont set
    private Date published; //can set
    private int rating; // dont set
    private int author; // dont set
    private String description; //can set
    private String edition; //can set
    private String isbn; //can set
    private String title; // set

    private Book(){
        // Privatise default constructor to force initialisation of required fields.
    }

    public Book(String title, int author){
        this.title = title;
        this.author = author;

        this.isRead = false;
        this.published = null;
        this.rating = 0;
        this.author = -1;
        this.description = "";
        this.edition = "";
        this.isbn = "";
    }

    public Book(String title,
                int author,
                Date published,
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



    public boolean getisRead(){ return isRead; }
    public Date getPublished(){ return published; }
    public int getRating(){ return rating; }
    public int getAuthor(){ return author; }
    public String getDescription(){ return description; }
    public String getEdition(){ return edition; }
    public String getIsbn(){ return isbn; }
    public String title(){ return title; }

    public void setIsRead(boolean isRead){
        this.isRead = isRead;
    }

    public void setPublished(Date published){
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
