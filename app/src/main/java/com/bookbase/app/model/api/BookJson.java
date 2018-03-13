package com.bookbase.app.model.api;

import java.util.List;
import java.util.Map;

public class BookJson {

    VolumeInfo volumeInfo;

    @Override
    public String toString() {
        return volumeInfo.toString();
    }

    public String getTitle(){
        return volumeInfo.title;
    }

    public String getAuthor(){
        return volumeInfo.getAuthors();
    }

    public String getDescription(){
        return volumeInfo.description;
    }

    public String getImageLink(){
        return volumeInfo.getImageLink();
    }

}

class VolumeInfo{
    String title;
    List<String> authors;
    String description;
    ImageLinks imageLinks;

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Title: " + title + "\n");
        for(String s : authors){
            out.append(s.toString() + "\n");
        }
        out.append("Description: " + description + "\n");
        out.append("Image Link: " + imageLinks.thumbnail);
        return out.toString();
    }

    public String getAuthors(){
        StringBuilder sb = new StringBuilder();
        for(String s : authors){
            if(authors.size() > 1){
                sb.append(s + ", ");
            } else{
                sb.append(s);
            }

        }
        return sb.toString();
    }

    String getImageLink(){
        return imageLinks.thumbnail;
    }
}

class Authors {

    String name;

    @Override
    public String toString(){
        return name;
    }

}

class ImageLinks{

    String thumbnail;

    @Override
    public String toString() {
        return thumbnail;
    }

}