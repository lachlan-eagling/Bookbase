package com.bookbase.bookbase.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bookbase.bookbase.utils.Converters;

import java.io.ByteArrayOutputStream;

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
    private String isbn;
    private String title;
    private String review;
    private byte[] image;
    private Long purchaseDate;
    private Double purchasePrice;

    public Book(){
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
        this.isbn = "";

    }

    @Ignore
    public Book(String title,
                Author author,
                int published,
                String description,
                String isbn,
                String review,
                Bitmap image,
                Long purchaseDate,
                Double purchasePrice){

        this.title = title;
        this.author = Converters.authorToInt(author);
        this.published = published;
        this.description = description;
        this.isbn = isbn;
        this.review = review;
        //this.image = imageToBase64(image);
        this.image = imageToByteArray(image);
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
    public String getIsbn(){ return isbn; }
    public String getTitle(){ return title; }
    public String getReview() { return review; }
    public byte[] getImage() { return image; }
    public Long getPurchaseDate() { return this.purchaseDate; }
    public Double getPurchasePrice() { return this.purchasePrice; }
    public Bitmap getRawImage(){ return byteArrayToImage(this.image); }

    public void setUid(int uid){ this.uid = uid; }
    public void setIsRead(boolean isRead){ this.isRead = isRead; }
    public void setPublished(int published){ this.published = published; }
    public void setRating(int rating){ this.rating = rating; }
    public void setAuthor(int author){ this.author = author; }
    public void setDescription(String description){ this.description = description; }
    public void setIsbn(String isbn){ this.isbn = isbn; }
    public void setTitle(String title){ this.title = title; }
    public void setReview(String review){ this.review = review; }
    public void setPurchaseDate(Long date){ this.purchaseDate = date; }
    public void setPurchasePrice(Double price){ this.purchasePrice = price; }
    public void setImage(byte[] image){ this.image = image; }

    public void setRawImage(Bitmap image){ this.image = imageToByteArray(image); }

    private byte[] imageToByteArray(Bitmap image){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, out);
        return out.toByteArray();
    }

    private Bitmap byteArrayToImage(byte[] data){
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public String authorName(){
        // TODO: return name of author, will need to implement query in AuthorDao to get author name that that takes author uid as param.
        return "Dummy Author";
    }

}
