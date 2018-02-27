package com.bookbase.app.library.addBook;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bookbase.app.R;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.repository.Repository;
import com.bookbase.app.utils.SaveImageHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddBookActivity extends AppCompatActivity {

    FragmentPagerAdapter viewPagerAdapter;
    private Bitmap imageToStore;
    final Book book = new Book();
    private Repository repository;

    public interface AddBookCallback{
        void inProgress();
        void onSuccess();
        void onFailure();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = Repository.getRepository();

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

            book.setTitle(title.getText().toString());
            book.setAuthor(new Author(author.getText().toString()));
            book.setGenre(new Genre(genre.getText().toString()));
            book.setDescription(description.getText().toString());
            book.setGenre(new Genre(genre.getText().toString()));
            book.setCoverImage(SaveImageHelper.saveImageToInternalStorage(imageToStore, book));
            book.setReview(review.getText().toString());
            book.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
            book.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
            book.setRating(((int)rating.getRating()));
            book.setIsRead(read.isChecked());

            repository.insertBook(book, new AddBookCallback() {
                @Override
                public void inProgress() {
                    // TODO: Display loading spinner.
                    showSnackBar("Inserting book...");
                    Log.d(this.getClass().getSimpleName(), "Inserting book");
                }

                @Override
                public void onSuccess() {
                    showSnackBar("Book inserted successfully...");
                    Log.d(this.getClass().getSimpleName(), "Book inserted");
                    finish();
                }

                @Override
                public void onFailure() {
                    // TODO: Display error and log to crash reporting.
                    showSnackBar("Book insert failed...");
                    Log.d(this.getClass().getSimpleName(), "Error inserting book");
                }
            });
            return true;
        } else{
            //Toast.makeText(this, "Missing mandatory fields!", Toast.LENGTH_SHORT).show();
            Snackbar.make(this.getCurrentFocus(), "Missing mandatory fields!", Snackbar.LENGTH_SHORT).show();
            return false;
        }

    }

    // TODO: Helper method for testing, remove this.
    private void showSnackBar(String message){
        Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
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
    }


    public void setImageToStore(Bitmap image){
        imageToStore = image;
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
