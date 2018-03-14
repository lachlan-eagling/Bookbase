package com.bookbase.app.model.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bookbase.app.model.entity.Review;

@Dao
public interface ReviewDao {

    @Query("SELECT * FROM Review WHERE ReviewId = :id")
    Review getReviewById(int id);

    @Insert
    long insert(Review review);
}
