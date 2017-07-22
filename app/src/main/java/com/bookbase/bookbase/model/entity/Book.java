package com.bookbase.bookbase.model.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.bookbase.bookbase.model.Converters;

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
    private String image;
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
        this.image = imageToBase64(image);
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
    public String getImage() { return image; }
    public Long getPurchaseDate() { return this.purchaseDate; }
    public Double getPurchasePrice() { return this.purchasePrice; }
    public Bitmap getRawImage(){ return base64toImage(this.image); }

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
    public void setImage(String image){ this.image = image; }
    public void setRawImage(Bitmap image){ this.image = imageToBase64(image); }

    private String imageToBase64(Bitmap image){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, out);

        byte[] bytes = out.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private Bitmap base64toImage(String in){
        byte[] data = Base64.decode(in, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

}
