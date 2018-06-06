package com.bookbase.app.model.repository;

/**
 * Callback interface for inserting book to database.
 * */
public interface AddBookCallback {

    /**
     * Database operation is in progress.
     * */
    void inProgress();

    /**
     * Database operation successful.
     * */
    void onSuccess();

    /**
     * Database operation failed.
     * */
    void onFailure();
}