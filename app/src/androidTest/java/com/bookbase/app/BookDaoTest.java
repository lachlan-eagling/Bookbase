package com.bookbase.app;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.model.dao.BookDao;
import com.bookbase.app.model.entity.BookImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class BookDaoTest {

    private BookDao bookDao;
    private AppDatabase testDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();
        testDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        bookDao = testDb.bookDao();
    }

    @After
    public void closeDb() throws IOException {
        testDb.close();
    }

    @Test
    public void insertAndAccessBook() throws Exception{
        BookImpl book = new BookImpl("Test BookImpl One", 1);
        bookDao.insertAll(book);
        List<BookImpl> books = bookDao.getBooks();
        assertThat(books.get(0).getTitle(), equalTo(book.getTitle()));
    }

}
