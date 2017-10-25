package com.bookbase.app.model.interfaces;

public interface Saveable<Saveable> {

    /**
     * Persists an object of type Saveable to the database.
     * @param object the object to be persisted.
     */
    void save(Saveable object);

    /**
     * Removes an object of type Saveable from the database.
     * @param object
     */
    void delete(Saveable object);
}
