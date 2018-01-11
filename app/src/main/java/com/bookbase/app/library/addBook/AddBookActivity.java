package com.bookbase.app.library.addBook;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.utils.AsyncInsertResponse;
import com.bookbase.app.utils.SaveImageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBookActivity extends AppCompatActivity implements AsyncInsertResponse {

    FragmentPagerAdapter viewPagerAdapter;
    private Bitmap imageToStore;
    final Book book = new Book();
    final Author _author = new Author();
    private long authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        final ViewPager addBooksPager = (ViewPager) findViewById(R.id.addBookPager);
        viewPagerAdapter = new AddBookPagerAdapter(getSupportFragmentManager());
        addBooksPager.setAdapter(viewPagerAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Add New Book");
        //toolbar.setTitle("Add Book")
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        TabLayout tabs = (TabLayout) findViewById(R.id.tab_slider);
        tabs.setupWithViewPager(addBooksPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment basicFragment = AddBookPagerAdapter.BASIC_DETAILS_PAGE;
        boolean mandatoryDetailsComplete = true;

        // Basic details
        EditText title = findViewById(R.id.add_book_title_data);
        EditText author = findViewById(R.id.add_book_author_data);
        EditText description = findViewById(R.id.add_book_description_data);
        EditText genre = findViewById(R.id.add_book_genre_data);
        ImageView coverImage = findViewById(R.id.cover_image);

        // Advanced details
        EditText review = findViewById(R.id.add_book_review_data);
        EditText purchaseDate = findViewById(R.id.add_book_purchase_date_data);
        EditText purchasePrice = findViewById(R.id.add_book_purchase_price_data);
        RatingBar rating = findViewById(R.id.add_book_rating_data);
        Switch read = findViewById(R.id.add_book_isread_data);
        Switch owned = findViewById(R.id.add_book_owned_data);

        if(title.getText().toString().trim().isEmpty()){
            title.setError("Title is required!");
            mandatoryDetailsComplete = false;
        }

        if(author.getText().toString().trim().isEmpty()){
            author.setError("Author is required!");
            mandatoryDetailsComplete = false;
        }

        if(description.getText().toString().trim().isEmpty()){
            description.setError("Description is required!");
            mandatoryDetailsComplete = false;
        }

        if(genre.getText().toString().trim().isEmpty()){
            genre.setError("Genre is required!");
            mandatoryDetailsComplete = false;
        }

        if(parseDate(purchaseDate.getText().toString()) == null){
            purchaseDate.setError("Incorrect date format!");
            mandatoryDetailsComplete = false;
        }

        if(mandatoryDetailsComplete){
            _author.setName(author.getText().toString());
            addAuthor(_author);
            return true;
        } else{
            Toast.makeText(this, "Missing mandatory fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private Calendar parseDate(String date){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYY", Locale.ENGLISH);

        try{
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e){
            e.printStackTrace();
        }

        return calendar;

    }

    private double parseDouble(String in){
        try {
            return Double.parseDouble(in);
        } catch(NumberFormatException e){
            return 0.0;
        }
        //return Double.parseDouble(in);
    }

    private synchronized void addBook(){
        // Basic details
        EditText title = findViewById(R.id.add_book_title_data);
        EditText description = findViewById(R.id.add_book_description_data);
        EditText genre = findViewById(R.id.add_book_genre_data);
        ImageView coverImage = findViewById(R.id.cover_image);

        // Advanced details
        EditText review = findViewById(R.id.add_book_review_data);
        EditText purchaseDate = findViewById(R.id.add_book_purchase_date_data);
        EditText purchasePrice = findViewById(R.id.add_book_purchase_price_data);
        RatingBar rating = findViewById(R.id.add_book_rating_data);
        Switch read = findViewById(R.id.add_book_isread_data);
        Switch owned = findViewById(R.id.add_book_owned_data);

        // Set basic details.
        book.setTitle(title.getText().toString());
        book.setAuthor(((int) authorId));
        book.setDescription(description.getText().toString());
        book.setGenre(new Genre(genre.getText().toString()));
        book.setCoverImage(SaveImageHelper.saveImageToInternalStorage(imageToStore, book));

        // Set advanced details.
        book.setReview(review.getText().toString());
        book.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
        book.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
        book.setRating(rating.getNumStars());
        book.setIsRead(read.isChecked());
        book.setIsOwned(owned.isChecked());

        AppDatabase db = AppDatabase.getDatabase(this);
        db.bookDao().insert(book);
    }

    private synchronized void addAuthor(Author author){
        InsertAuthorAsyncTask insertAuthorTask = new InsertAuthorAsyncTask();
        insertAuthorTask.delegate = this;
        insertAuthorTask.context = this;
        insertAuthorTask.author = author;
        insertAuthorTask.execute(author);
    }

    public void setImageToStore(Bitmap image){
        imageToStore = image;
    }

    @Override
    public void postResult(long Out) {
        authorId = Out;

        new Thread(new Runnable() {
            @Override
            public void run() {
                addBook();
            }
        }).start();

        finish();
    }

    public static class AddBookPagerAdapter extends FragmentPagerAdapter{

        private static int NUM_ITEMS = 2;
        private static Fragment BASIC_DETAILS_PAGE;
        private static Fragment ADVANCED_DETAILS_PAGE;

        public AddBookPagerAdapter(android.support.v4.app.FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public int getCount(){
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {

            switch(position){
                case 0:
                    BASIC_DETAILS_PAGE = AddBooksFragmentBasic.newInstance(1, "Basic");
                    return BASIC_DETAILS_PAGE;
                case 1:
                    ADVANCED_DETAILS_PAGE = AddBooksFragmentAdvanced.newInstance(2, "Advanced");
                    return ADVANCED_DETAILS_PAGE;
                default:
                    return null;
            }
        }


        @Override
        public CharSequence getPageTitle(int position){
            return getItem(position).getArguments().getString("title");
        }

    }

    // Async task to insert new author.
    public class InsertAuthorAsyncTask extends AsyncTask {

        public AsyncInsertResponse delegate = null;
        public Author author;
        public Context context;

        @Override
        protected Object doInBackground(Object[] objects) {
            if(context != null && author != null) {
                AppDatabase db = AppDatabase.getDatabase(context);
                return db.authorDao().insert((Author) objects[0]);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            // Post result back to activity.
            try{
                delegate.postResult((long) o);
            } catch(ClassCastException e) {
                e.printStackTrace();
            }
        }
    }

}
