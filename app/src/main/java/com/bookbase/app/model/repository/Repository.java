package com.bookbase.app.model.repository;

import android.content.Context;

import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.library.addBook.AddBookActivity;
import com.bookbase.app.model.dao.AuthorDao;
import com.bookbase.app.model.dao.BookDao;
import com.bookbase.app.model.dao.GenreDao;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;

import java.util.ArrayList;
import java.util.List;
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
    BookDao bookDao;
    AuthorDao authorDao;
    GenreDao genreDao;

    private Repository(Context context){
        final BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        pool = new ThreadPoolExecutor(
                NUM_CORES,
                NUM_CORES,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                workQueue);

        AppDatabase db = AppDatabase.getDatabase(context);
        bookDao = db.bookDao();
        authorDao = db.authorDao();
        genreDao = db.genreDao();
    }

    public static Repository getRepository(Context context){
        if(repository == null){
            repository = new Repository(context);
        }
        return  repository;
    }

    public List<Book> getBookList() {
        // TODO: Off load to background thread.
        return bookDao.getBooks();
    }

    public Book getBook(final int bookId) {
        return bookDao.getSingleBook(bookId);
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

    public void insertBookList(final List<Book> books) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bookDao.insertAll(books);
            }
        };
        pool.execute(runnable);
    }

    public void updateBook(final Book book, final AddBookActivity.AddBookCallback callback){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long success;
                callback.inProgress();
                success = bookDao.update(book);
                if(success > 0){
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }
        };
        pool.execute(runnable);
    }

    public void deleteBook(final Book book) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                bookDao.deleteBook(book);
            }
        };
        pool.execute(runnable);
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

    public List<String> getAuthorNames(){
        final List<String> authorNames = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                authorNames.addAll(authorDao.getAuthorNames());
            }
        };
        pool.execute(runnable);
        return authorNames;
    }

   public List<String> getGenreNames() {
        final List<String> genres = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                genres.addAll(genreDao.getGenreNames());
            }
        };
        pool.execute(runnable);
        return genres;
   }

   public Author getAuthorByName(final String name) {
        final List<Author> author = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                author.add(authorDao.getAuthorByName(name));
            }
        };
        pool.execute(runnable);
        if(author.isEmpty()){
            return null;
        }
        return author.get(0);
   }

   public Genre getGenreByName(final String name) {
        final List<Genre> genre = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                genre.add(genreDao.getGenreByName(name));
            }
        };
        pool.execute(runnable);
        if(genre.isEmpty()) {
            return null;
        }
        return genre.get(0);
   }

   public List<Author> getAuthors() {
        final List<Author> authors = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                authors.addAll(authorDao.getAuthors());
            }
        };
        pool.execute(runnable);
        return authors;
   }

   public List<Genre> getGenres() {
        final List<Genre> genres = new ArrayList<>();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                genres.addAll(genreDao.getGenres());
            }
        };
        pool.execute(runnable);
        return genres;
   }
}
