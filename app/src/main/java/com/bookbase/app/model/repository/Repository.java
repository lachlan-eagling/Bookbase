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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Repository {

    // Thread pool parameters.
    private static int NUM_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    private ThreadPoolExecutor pool;

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
        final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        pool = new ThreadPoolExecutor(
                NUM_CORES,
                NUM_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue);

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

    public List<Book> getBookList() {
        // TODO: Off load to background thread.
        return bookDao.getBooks();
    }

    public void insertBook(final Book book, final AddBookActivity.AddBookCallback callback){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long success;
                callback.inProgress();
                success = bookDao.insert(book);
                if(success > 0){
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }
        };
        pool.execute(runnable);
    }

    public int deleteBook(Book book) {
        // TODO: Off load to background thread.
        return bookDao.deleteBook(book);
    }

    public void insertAuthor(final Author author){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                authorDao.insert(author);
            }
        };
        pool.execute(runnable);
    }

    public void insertGenre(final Genre genre){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                genreDao.insert(genre);
            }
        };
        pool.execute(runnable);
    }

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
