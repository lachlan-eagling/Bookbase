package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;

@Entity(tableName = "Review")
public class Review {

    @PrimaryKey(autoGenerate = true)
    private int reviewId;
    private Calendar reviewDate;
    private String reviewContent;

    public Review() {
        this(Calendar.getInstance(), "");
    }

    public Review(Calendar reviewDate, String reviewContent){
        this.reviewDate = reviewDate;
        this.reviewContent = reviewContent;
    }

    public int getReviewId() {
        return this.reviewId;
    }

    public Calendar getReviewDate() {
        return reviewDate;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewId(int id) {
        this.reviewId = id;
    }

    public void setReviewDate(Calendar reviewDate) {
        this.reviewDate = reviewDate;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

}
