package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.bookbase.app.utils.Converters;

import java.util.Calendar;

@Entity(tableName = "Book")
public class Book implements Parcelable{

    //, foreignKeys = @ForeignKey(entity = Author.class, parentColumns = "authorId", childColumns = "author", onDelete = CASCADE)

    @PrimaryKey(autoGenerate = true)
    private int bookId;
    private String title;
    private Author author;
    private String description;
    private Genre genre;
    private String coverImage;
    private String isbn;
    private int rating;
    private String review;
    private boolean isRead;
    private Calendar purchaseDate;
    private double purchasePrice;
    private boolean isOwned;

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[0];
        }
    };

    // Default constructor for Room database.
    public Book(){
        
    }

    @Ignore
    public Book(String title, Author author){
        this.title = title;
        this.author = author;
        this.isRead = false;
        this.rating = 0;
        this.author = author;
        this.description = "";
        this.isbn = "";
        this.genre = new Genre("");
        this.review = "";

    }

    @Ignore
    public Book(String title, Author author, String description, Genre genre){
        this.title = title;
        this.author = author;
        this.isRead = false;
        this.rating = 0;
        this.author = author;
        this.description = "";
        this.isbn = "";
        this.genre = new Genre("");
        this.review = "";

    }


    public Book(int bookId, boolean isRead, int rating, Author author, String description,
                Genre genre, String isbn, String title, String review, String coverImage,
                Calendar purchaseDate, double purchasePrice, boolean isOwned) {
        this.bookId = bookId;
        this.isRead = isRead;
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
    public int getRating(){ return rating; }
    public Author getAuthor(){ return author; }
    public String getDescription(){ return description; }
    public Genre getGenre() { return genre; }
    public String getIsbn(){ return isbn; }
    public String getTitle(){ return title; }
    public String getReview() { return review; }
    public Calendar getPurchaseDate() { return purchaseDate; }
    public double getPurchasePrice() { return purchasePrice; }
    public String getCoverImage() { return coverImage; }
    public boolean getIsOwned() { return isOwned; }

    public void setBookId(int bookId){ this.bookId = bookId; }
    public void setIsRead(boolean isRead){ this.isRead = isRead; }
    public void setRating(int rating){ this.rating = rating; }
    public void setAuthor(Author author){ this.author = author; }
    public void setDescription(String description){ this.description = description; }
    public void setGenre(Genre genre) { this.genre = genre; }
    public void setIsbn(String isbn){ this.isbn = isbn; }
    public void setTitle(String title){ this.title = title; }
    public void setReview(String review){ this.review = review; }
    public void setPurchaseDate(Calendar date){ this.purchaseDate = date; }
    public void setPurchasePrice(double price){ this.purchasePrice = price; }
    public void setCoverImage(String imageDirectory) { this.coverImage = imageDirectory; }
    public void setIsOwned(boolean isOwned) { this.isOwned = isOwned; }

    public Bundle bundleBook(){
        Bundle bundle = new Bundle();
        bundle.putInt("bookId", bookId);
        bundle.putString("title", title);
        //bundle.putInt("author", author);
        //bundle.putInt("genre", genre) //TODO: Need to fix so can bundle Genre val.
        bundle.putString("coverImage", coverImage);
        bundle.putString("isbn", isbn);
        bundle.putInt("rating", rating);
        bundle.putString("review", review);
        bundle.putBoolean("isRead", isRead);
        bundle.putString("purchaseDate", Converters.calendarToString(purchaseDate));
        bundle.putDouble("price", purchasePrice);
        bundle.putBoolean("owned", isOwned);
        return bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
