package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.bookbase.app.utils.Converters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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
    private Review review;
    private boolean isRead;
    private Calendar purchaseDate;
    private double purchasePrice;

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(title);
        dest.writeParcelable(author, flags);
        dest.writeString(description);
        dest.writeParcelable(genre, flags);
        dest.writeString(coverImage);
        dest.writeString(isbn);
        dest.writeInt(rating);
        dest.writeParcelable(review, flags);
        dest.writeByte((byte) (isRead ? 1 : 0));
        dest.writeString(Converters.calendarToString(purchaseDate));
        dest.writeDouble(purchasePrice);
    }

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
        this.review = new Review();

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
        this.review = new Review();

    }

    @Ignore
    private Book(Parcel in){
        bookId = in.readInt();
        title = in.readString();
        author = in.readParcelable(Author.class.getClassLoader());
        description = in.readString();
        genre = in.readParcelable(Genre.class.getClassLoader());
        coverImage = in.readString();
        isbn = in.readString();
        rating = in.readInt();
        review = in.readParcelable(Review.class.getClassLoader());
        isRead = in.readByte() == 1;
        purchaseDate = Converters.toCalendar(in.readString());
        purchasePrice = in.readDouble();

    }

    public Book(int bookId, boolean isRead, int rating, Author author, String description,
                Genre genre, String isbn, String title, Review review, String coverImage,
                Calendar purchaseDate, double purchasePrice, boolean isOwned) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.genre = genre;
        this.coverImage = coverImage;
        this.isbn = isbn;
        this.rating = rating;
        this.review = review;
        this.isRead = isRead;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
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

    public String getPurchaseDateString(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String date = df.format(Calendar.getInstance().getTime());
        if (purchaseDate != null) {
            date = df.format(purchaseDate.getTime());
        }
        return date;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null || getClass() != obj.getClass()) { return false; }
        Book book = (Book) obj;
        if (bookId != book.getBookId()) { return false; }
        return title != null ? title.equals(book.getTitle()) : book.getTitle() == null;
    }

    @Override
    public int hashCode() {
        int result = (bookId ^ (bookId >>> 8));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

}
