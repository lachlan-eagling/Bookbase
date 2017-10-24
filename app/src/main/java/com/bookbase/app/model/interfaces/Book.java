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

    /**
     * Reference to a Genre type.
     * @param genre references genreId of a Genre object.
     */
    void setGenre(int genre);
    void setIsOwned(boolean isOwned);

    /**
     * Reference to a Review type.
     * @param review references reviewId of a Review object.
     */
    void setReview(int review);

    String getTitle();

    /**
     * @return a reference to the directory where image is stored on device.
     */
    String getCoverImage();
    Calendar getPurchaseDate();
    double getPurchasePrice();
    int getReview();
    int getRating();
    String getDescription();
    boolean getIsRead();
    int getGenre();
    boolean getIsOwned();


}
