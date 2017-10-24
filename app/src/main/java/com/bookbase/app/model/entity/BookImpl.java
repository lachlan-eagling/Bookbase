package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.model.interfaces.Book;

import java.util.Calendar;

@Entity
public class BookImpl implements Book{

    @PrimaryKey(autoGenerate = true)
    private int bookId;

    private boolean isRead;
    private int published;
    private int rating;
    private int author;
    private String description;

    private int genre;
    private String isbn;
    private String title;
    private int review;
    private String coverImage;
    private Calendar purchaseDate;
    private double purchasePrice;
    private boolean isOwned;

    // Default constructor for Room database.
    public BookImpl(){

    }

    @Ignore
    public BookImpl(String title, int author){
        this.title = title;
        this.author = author;

        this.isRead = false;
        this.published = 0;
        this.rating = 0;
        this.author = -1;
        this.description = "";
        this.isbn = "";

    }


    public BookImpl(int bookId, boolean isRead,
                    int published, int rating,
                    int author, String description,
                    int genre, String isbn, String title,
                    int review, String coverImage,
                    Calendar purchaseDate,
                    double purchasePrice, boolean isOwned) {
        this.bookId = bookId;
        this.isRead = isRead;
        this.published = published;
        this.rating = rating;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.isbn = isbn;
        this.title = title;
        this.review = review;
        this.coverImage = coverImage;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.isOwned = isOwned;
    }

    public int getBookId(){ return bookId; }
    public boolean getIsRead(){ return isRead; }
    public int getPublished(){ return published; }
    public int getRating(){ return rating; }
    public int getAuthor(){ return author; }
    public String getDescription(){ return description; }
    public int getGenre() { return genre; }
    public String getIsbn(){ return isbn; }
    public String getTitle(){ return title; }
    public int getReview() { return review; }
    public Calendar getPurchaseDate() { return purchaseDate; }
    public double getPurchasePrice() { return purchasePrice; }
    public String getCoverImage() { return coverImage; }
    public boolean getIsOwned() { return isOwned; }

    public void setBookId(int bookId){ this.bookId = bookId; }
    public void setIsRead(boolean isRead){ this.isRead = isRead; }
    public void setPublished(int published){ this.published = published; }
    public void setRating(int rating){ this.rating = rating; }
    public void setAuthor(int author){ this.author = author; }
    public void setDescription(String description){ this.description = description; }
    public void setGenre(int genre) { this.genre = genre; }
    public void setIsbn(String isbn){ this.isbn = isbn; }
    public void setTitle(String title){ this.title = title; }
    public void setReview(int review){ this.review = review; }
    public void setPurchaseDate(Calendar date){ this.purchaseDate = date; }
    public void setPurchasePrice(double price){ this.purchasePrice = price; }
    public void setCoverImage(String imageDirectory) { this.coverImage = imageDirectory; }
    public void setIsOwned(boolean isOwned) { this.isOwned = isOwned; }

}
