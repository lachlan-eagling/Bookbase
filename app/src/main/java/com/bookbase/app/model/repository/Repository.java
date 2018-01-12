package com.bookbase.app.model.repository;

import java.util.List;

public interface Repository<T> {

    T get(int itemId);
    List<T> getAll();
    int update(T item);
    int delete(T item);

}
