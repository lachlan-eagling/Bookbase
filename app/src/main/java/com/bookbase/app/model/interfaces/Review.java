package com.bookbase.app.model.interfaces;

import java.util.Calendar;

public interface Review {

    int getReviewId();
    Calendar getReviewDate();
    String getReviewContent();

    void setReviewId(int id);
    void setReviewDate(Calendar reviewDate);
    void setReviewContent(String reviewContent);
}
