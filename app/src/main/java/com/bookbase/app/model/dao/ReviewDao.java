package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.ReviewImpl;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM Review WHERE reviewId=:id")
    ReviewImpl getReviewById(int id);

}
