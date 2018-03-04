package com.bookbase.app.model.api;

import java.util.List;
import java.util.Map;

public class BookJson {

    VolumeInfo volumeInfo;

    @Override
    public String toString() {
        return volumeInfo.toString();
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