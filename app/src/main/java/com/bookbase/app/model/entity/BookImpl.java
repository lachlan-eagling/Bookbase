package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.model.interfaces.Author;
import com.bookbase.app.model.interfaces.Book;
import com.bookbase.app.model.interfaces.Genre;
import com.bookbase.app.model.interfaces.Review;

import java.util.Calendar;

@Entity(tableName = "Book")
public class BookImpl implements Book{

    @PrimaryKey(autoGenerate = true)
    private int bookId;

    // Basic properties.
    private String title;
    private Author author;
    private String description;
    private Genre genre;

    // Advanced properties.
    private boolean isRead;
    private int rating;
    private String isbn;
    private Review review;
    private String coverImage;
    private Calendar purchaseDate;
    private double purchasePrice;
    private boolean isOwned;

    // Default constructor for Room database.
    public BookImpl(){
        this("", new AuthorImpl("", ""));
    }

    @Ignore
    public BookImpl(String title, Author author){
        this.title = title;
        this.author = author;
        this.isRead = false;
        this.rating = 0;
        this.author = new AuthorImpl("James", "McBookFace");
        this.description = "";
        this.isbn = "";
        this.genre = new GenreImpl();
        this.review = new ReviewImpl();

    }

    @Ignore
    public BookImpl(String title, Author author, String description, Genre genre){
        this.title = title;
        this.author = author;
        this.isRead = false;
        this.rating = 0;
        this.author = new AuthorImpl("James", "McBookFace");
        this.description = "";
        this.isbn = "";
        this.genre = new GenreImpl();
        this.review = new ReviewImpl();

    }


    public BookImpl(int bookId,
                    boolean isRead,
                    int rating,
                    Author author,
                    String description,
                    Genre genre,
                    String isbn,
                    String title,
                    Review review,
                    String coverImage,
                    Calendar purchaseDate,
                    double purchasePrice,
                    boolean isOwned) {
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
    public Review getReview() { return review; }
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
    public void setReview(Review review){ this.review = review; }
    public void setPurchaseDate(Calendar date){ this.purchaseDate = date; }
    public void setPurchasePrice(double price){ this.purchasePrice = price; }
    public void setCoverImage(String imageDirectory) { this.coverImage = imageDirectory; }
    public void setIsOwned(boolean isOwned) { this.isOwned = isOwned; }

}
