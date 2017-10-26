package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.model.interfaces.Review;

import java.util.Calendar;

@Entity(tableName = "Review")
public class ReviewImpl implements Review {

    @PrimaryKey
    private int reviewId;
    private Calendar reviewDate;
    private String reviewContent;

    public ReviewImpl() {
        this(Calendar.getInstance(), "");
    }

    public ReviewImpl(Calendar reviewDate, String reviewContent){
        this.reviewDate = reviewDate;
        this.reviewContent = reviewContent;
    }

    @Override
    public int getReviewId() {
        return this.reviewId;
    }

    @Override
    public Calendar getReviewDate() {
        return reviewDate;
    }

    @Override
    public String getReviewContent() {
        return reviewContent;
    }

    @Override
    public void setReviewId(int id) {
        this.reviewId = id;
    }

    @Override
    public void setReviewDate(Calendar reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

}
