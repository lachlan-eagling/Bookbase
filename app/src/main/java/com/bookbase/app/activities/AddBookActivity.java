package com.bookbase.app.activities;

import android.graphics.Bitmap;
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
import com.bookbase.app.fragments.AddBooksFragmentAdvanced;
import com.bookbase.app.fragments.AddBooksFragmentBasic;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBookActivity extends AppCompatActivity {

    FragmentPagerAdapter viewPagerAdapter;

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
        final Book book = new Book();
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

            //final Book book = new Book(title.getText().toString(), new Author(author.getText().toString(), ""), description.getText().toString(), new Genre());

            // Set basic details.
            book.setTitle(title.getText().toString());
            book.setAuthor(new Author(author.getText().toString()));
            book.setDescription(description.getText().toString());
            book.setGenre(new Genre(genre.getText().toString()));
            //book.setCoverImage(SaveImageHelper.saveImageToInternalStorage(getCoverImage(coverImage), book));

            // Set advanced details.
            book.setReview(review.getText().toString());
            book.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
            book.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
            book.setRating(rating.getNumStars());
            book.setIsRead(read.isChecked());
            book.setIsOwned(owned.isChecked());

            // Insert book to db on new thread.
            new Thread(new Runnable() {
                @Override
                public void run() {
                    addBook(book);
                }
            }).start();

            finish();
            return true;

        } else{
            Toast.makeText(this, "Missing mandatory fields!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private Bitmap getCoverImage(ImageView imageView){
        return Bitmap.createBitmap(imageView.getDrawingCache());
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
        return Double.parseDouble(in);
    }

    private synchronized void addBook(Book book){
        AppDatabase db = AppDatabase.getDatabase(this);
        db.bookDao().insert(book);
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

}
