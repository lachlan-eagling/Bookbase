package com.bookbase.app.model.interfaces;

import java.util.Calendar;

public interface Book {

    void setTitle(String title);

    /**
     * Cover image is stored on device local storage with a reference to this persisted in db.
     * @param imageDirectory a reference to the image in devices local storage.
     */
    void setCoverImage(String imageDirectory);

    void setPurchaseDate(Calendar date);

    void setPurchasePrice(double purchasePrice);

    void setRating(int rating);

    void setDescription(String description);

    void setIsRead(boolean isRead);

    void setGenre(Genre genre);

    void setIsOwned(boolean isOwned);

    void setReview(Review review);

    String getTitle();

    /**
     * @return a reference to the directory where image is stored on device.
     * The retrieval of the actual image from device storage is delegated to the controller.
     */
    String getCoverImage();
    Calendar getPurchaseDate();
    double getPurchasePrice();
    Review getReview();
    int getRating();
    String getDescription();
    boolean getIsRead();
    Genre getGenre();
    boolean getIsOwned();


}
