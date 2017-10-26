package com.bookbase.app.model.interfaces;

public interface Author {

    int getAuthorId();
    String getFirstName();
    String getLastName();

    void setAuthorId(int authorId);
    void setFirstName(String firstName);
    void setLastName(String lastName);
}
