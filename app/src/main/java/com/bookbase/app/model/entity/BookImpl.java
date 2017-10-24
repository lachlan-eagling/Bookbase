package com.bookbase.app.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.utils.Converters;

@Entity
public class BookImpl{

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name="is_read")
    private boolean isRead;
    private int published;
    private int rating;
    private int author;
    private String description;
    private String genre;
    private String isbn;
    private String title;
    private String review;
    //private byte[] image;
    private String image;
    private Long purchaseDate;
    private Double purchasePrice;

    public BookImpl(){
        // Privatise default constructor to force initialisation of required fields.
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

    @Ignore
    public BookImpl(String title,
                    Author author,
                    int published,
                    String description,
                    String genre,
                    String isbn,
                    String review,
                    String image,
                    Long purchaseDate,
                    Double purchasePrice){

        this.title = title;
        this.author = Converters.authorToInt(author);
        this.published = published;
        this.description = description;
        this.genre = genre;
        this.isbn = isbn;
        this.review = review;
        this.image = image;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;

        this.isRead = false;
        this.rating = 0;

    }

    public int getUid(){ return uid; }
    public boolean getIsRead(){ return isRead; }
    public int getPublished(){ return published; }
    public int getRating(){ return rating; }
    public int getAuthor(){ return author; }
    public String getDescription(){ return description; }
    public String getGenre() { return genre; }
    public String getIsbn(){ return isbn; }
    public String getTitle(){ return title; }
    public String getReview() { return review; }
    public String getImage() { return image; }
    public Long getPurchaseDate() { return this.purchaseDate; }
    public Double getPurchasePrice() { return this.purchasePrice; }

    public void setUid(int uid){ this.uid = uid; }
    public void setIsRead(boolean isRead){ this.isRead = isRead; }
    public void setPublished(int published){ this.published = published; }
    public void setRating(int rating){ this.rating = rating; }
    public void setAuthor(int author){ this.author = author; }
    public void setDescription(String description){ this.description = description; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setIsbn(String isbn){ this.isbn = isbn; }
    public void setTitle(String title){ this.title = title; }
    public void setReview(String review){ this.review = review; }
    public void setPurchaseDate(Long date){ this.purchaseDate = date; }
    public void setPurchasePrice(Double price){ this.purchasePrice = price; }
    public void setImage(String imagee){ this.image = image; }

    public String authorName(){
        // TODO: return name of author, will need to implement query in AuthorDao to get author name that that takes author uid as param.
        return "Dummy Author";
    }

}
