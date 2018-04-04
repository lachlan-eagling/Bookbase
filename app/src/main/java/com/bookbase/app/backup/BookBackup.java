package com.bookbase.app.backup;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.bookbase.app.utils.Converters;
import com.squareup.moshi.ToJson;

import java.util.Calendar;

public class BookBackup {

    private int bookId;
    private String bookTitle;
    private String description;
    private String coverImage;
    private String isbn;
    private int rating;
    private boolean isRead;
    private long purchaseDate;
    private double purchasePrice;

    private int authorId;
    private String authorName;

    private int genreId;
    private String genreName;

    private int reviewId;
    private long reviewDate;
    private String reviewContent;

    protected BookBackup() {

    }

    protected int getBookId() {
        return bookId;
    }

    protected void setBookId(int bookId) {
        this.bookId = bookId;
    }

    protected String getBookTitle() {
        return bookTitle;
    }

    protected void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    protected String getDescription() {
        return description;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected String getCoverImage() {
        return coverImage;
    }

    protected void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    protected String getIsbn() {
        return isbn;
    }

    protected void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    protected int getRating() {
        return rating;
    }

    protected void setRating(int rating) {
        this.rating = rating;
    }

    protected boolean isRead() {
        return isRead;
    }

    protected void setRead(boolean read) {
        isRead = read;
    }

    protected long getPurchaseDate() {
        return purchaseDate;
    }

    protected void setPurchaseDate(long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    protected double getPurchasePrice() {
        return purchasePrice;
    }

    protected void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    protected int getAuthorId() {
        return authorId;
    }

    protected void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    protected String getAuthorName() {
        return authorName;
    }

    protected void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    protected int getGenreId() {
        return genreId;
    }

    protected void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    protected String getGenreName() {
        return genreName;
    }

    protected void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    protected int getReviewId() {
        return reviewId;
    }

    protected void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    protected Long getReviewDate() {
        return reviewDate;
    }

    protected void setReviewDate(long reviewDate) {
        this.reviewDate = reviewDate;
    }

    protected String getReviewContent() {
        return reviewContent;
    }

    protected void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
