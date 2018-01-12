package com.bookbase.app.model.repository;

import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.dao.BookDao;
import com.bookbase.app.model.entity.Book;

import java.util.List;

public class BookRepository implements Repository<Book>{

    private AppDatabase db;
    private static BookRepository repository;
    private BookDao dao;

    private BookRepository(){
        dao = AppDatabase.getDatabase(HomeScreen.getContext()).bookDao();
    }

    public static BookRepository getRepository(){
        if(repository == null){
            repository = new BookRepository();
        }
        return  repository;
    }

    @Override
    public Book get(int bookId) {
        return dao.getSingleBook(bookId);
    }

    @Override
    public List<Book> getAll() {
        return dao.getBooks();
    }

    @Override
    public int update(Book book) {
        return dao.update(book);
    }

    @Override
    public int delete(Book book) {
        return dao.deleteBook(book);
    }
}
