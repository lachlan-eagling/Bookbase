package com.bookbase.app.model.repository;

import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.library.addBook.AddBookActivity;
import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.dao.AuthorDao;
import com.bookbase.app.model.dao.BookDao;
import com.bookbase.app.model.dao.GenreDao;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

    private static Repository repository;
    private BookDao bookDao;
    private AuthorDao authorDao;
    private GenreDao genreDao;

    private List<Book> books;
    private List<Author> authors;
    private List<Genre> genres;
    private Map<Integer, String> authorMap;
    private Map<Integer, String> genreMap;

    private Repository(){
        AppDatabase db = AppDatabase.getDatabase(HomeScreen.getContext());
        bookDao = db.bookDao();
        authorDao = db.authorDao();
        genreDao = db.genreDao();

        books = bookDao.getBooks();
        authors = authorDao.getAuthors();
        genres = genreDao.getGenres();
        authorMap = new HashMap<>();
        genreMap = new HashMap<>();

        refreshGenreMap();
        refreshAuthorMap();
    }

    public static Repository getRepository(){
        if(repository == null){
            repository = new Repository();
        }
        return  repository;
    }

    public boolean addNewBook(Book book, Author author, Genre genre, AddBookActivity.AddBookCallback callback){
        /*
        * OFFLOAD ALL OF THIS TO ANOTHER THREAD.
        * 1. Call callback.inProgress() to start loading spinner in UI.
        * 2. Insert Author and Genre, get id's back.
        * 3. Set author and genre fields of book.
        * 4. Insert book.
        * 5. Call either onSuccess() or onFailure() callback methods depending on outcome.
        * */
        return true;
    }

    // Book methods.
    public Book getBook(int bookId) {
        // TODO: Off load to background thread.
        return bookDao.getSingleBook(bookId);
    }

    public List<Book> getBookList() {
        // TODO: Off load to background thread.
        if(books.isEmpty() || books == null){
            books = bookDao.getBooks();
        }
        return books;
    }

    public int updateBook(Book book) {
        // TODO: Off load to background thread.
        return bookDao.update(book);
    }

    public int deleteBook(Book book) {
        // TODO: Off load to background thread.
        return bookDao.deleteBook(book);
    }

    // Author Methods.
    public String getAuthorName(int authorId){
        String name = authorMap.get(authorId);
        return name != null ? name : "No Author Name";
    }

    // Genre Methods.
    public String getGenreName(int genreId){
        String name = genreMap.get(genreId);
        return name != null ? name : "No Genre Name";
    }

    // Refresh methods.
    private void refreshBooks(){
        // TODO: Off load to background thread.
        if(books != null){
            bookDao.getBooks();
        }
    }

    private void refreshAuthors(){
        // TODO: Off load to background thread.
        if(authors != null){
            authors = authorDao.getAuthors();
            refreshAuthorMap();
        }
    }

    private void refreshGenres(){
        // TODO: Off load to background thread.
        if(genres != null){
            genres = genreDao.getGenres();
            refreshGenreMap();
        }
    }

    private void refreshAll(){
        refreshBooks();
        refreshAuthors();
        refreshGenres();
    }

    private void refreshAuthorMap(){
        if(authorMap != null){
            for(Author a : authors){
                authorMap.put(a.getAuthorId(), a.getName());
            }
        }
    }

    private void refreshGenreMap(){
        if(genreMap != null){
            for(Genre g : genres){
                genreMap.put(g.getGenreId(), g.getGenreName());
            }
        }
    }

}
