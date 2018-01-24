package com.bookbase.app.model.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "Author")
public class Author implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int authorId;
    private String name;

    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>(){
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(authorId);
        dest.writeString(name);
    }

    public Author(){
    }

    public Author(String name){
        this.name = name;
    }

    @Ignore
    private Author(Parcel in){
        authorId = in.readInt();
        name = in.readString();
    }

    public int getAuthorId(){ return authorId; }
    public String getName(){ return name; }

    public void setAuthorId(int authorId){
        this.authorId = authorId;
    }
    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
    
}
