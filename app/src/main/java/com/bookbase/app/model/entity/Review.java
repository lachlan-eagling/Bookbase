package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.bookbase.app.utils.Converters;

import java.util.Calendar;

@Entity
public class Review implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int reviewId;
    private Calendar reviewDate;
    private String reviewContent;

    private static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>(){
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[0];
        }
    };

    public Review(){
        this.reviewDate = Calendar.getInstance();
        this.reviewContent = "";
    }

    public Review(Calendar reviewDate, String reviewContent){
        this.reviewDate = reviewDate;
        this.reviewContent = reviewContent;
    }

    private Review(Parcel in){
        this.reviewId = in.readInt();
        this.reviewDate = Converters.toCalendar(in.readString());
        this.reviewContent = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reviewId);
        dest.writeString(Converters.calendarToString(reviewDate));
        dest.writeString(reviewContent);
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public Calendar getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Calendar reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }
}
