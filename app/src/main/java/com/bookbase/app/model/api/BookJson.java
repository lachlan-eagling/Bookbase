package com.bookbase.app.model.api;

import java.util.List;

@SuppressWarnings("unused")
class BookJson {

    @SuppressWarnings("unused")
    private VolumeInfo volumeInfo;

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

@SuppressWarnings("unused")
class VolumeInfo{
    String title;
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<String> authors;
    String description;
    private ImageLinks imageLinks;

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Title: ");
        out.append(title);
        out.append("\n");
        for(String s : authors){
            out.append(s);
            out.append("\n");
        }
        out.append("Description: ");
        out.append(description);
        out.append("\n");
        out.append("Image Link: ");
        out.append(imageLinks.thumbnail);
        return out.toString();
    }

    public String getAuthors(){
        StringBuilder sb = new StringBuilder();
        for(String s : authors){
            if(authors.size() > 1){
                sb.append(s);
                sb.append(", ");
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

@SuppressWarnings("ALL")
class Authors {

    private String name;

    @Override
    public String toString(){
        return name;
    }

}

@SuppressWarnings("unused")
class ImageLinks{

    String thumbnail;

    @Override
    public String toString() {
        return thumbnail;
    }

}