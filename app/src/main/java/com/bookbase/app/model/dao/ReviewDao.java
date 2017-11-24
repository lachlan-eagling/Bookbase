package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Review;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM Review WHERE reviewId=:id")
    Review getReviewById(int id);

}
