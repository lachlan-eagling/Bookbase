package com.bookbase.app.model.entity;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Genre implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int genreId;
    private String genreName;

    public static final Parcelable.Creator<Genre> CREATOR = new Parcelable.Creator<Genre>(){
        @Override
        public Genre createFromParcel(Parcel source) {
            return new Genre(source);
        }

        @Override
        public Genre[] newArray(int size) {
            return new Genre[size];
        }
    };
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(genreId);
        dest.writeString(genreName);
    }

    public Genre() {

    }

    public Genre(String genreName){
        this.genreName = genreName;
    }

    @Ignore
    Genre(Parcel in){
        genreId = in.readInt();
        genreName = in.readString();
    }

    public int getGenreId() {
        return genreId;
    }
    public String getGenreName() {
        return genreName;
    }

    
    public void setGenreId(int id) {
        this.genreId = id;
    }
    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

}
