package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.bookbase.app.model.interfaces.Review;

// TODO: Finish class implementation, have added this as a stub to get BookImpl working.

@Entity
public class ReviewImpl implements Review {

    @PrimaryKey
    private int reviewId;

    public ReviewImpl() {
    }

    @Override
    public int getReviewId() {
        return this.reviewId;
    }

    @Override
    public void setReviewId(int id) {
        this.reviewId = id;
    }
}
