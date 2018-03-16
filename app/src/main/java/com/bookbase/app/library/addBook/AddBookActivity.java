package com.bookbase.app.library.addBook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.bookbase.app.R;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.bookbase.app.model.repository.Repository;
import com.bookbase.app.utils.SaveImageHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBookActivity extends AppCompatActivity {

    private Bitmap imageToStore;
    final Book book = new Book();
    Book bookToEdit = null;
    private Repository repository;
    public static final int IMAGE_CAPTURE_REQUEST = 1;

    private List<Author> authors;
    private List<Genre> genres;

    @BindView(R.id.add_book_title_field) TextInputLayout titleField;
    @BindView(R.id.add_book_author_field) TextInputLayout authorField;
    @BindView(R.id.add_book_descr_field) TextInputLayout descrField;
    @BindView(R.id.add_book_genre_field) TextInputLayout genreField;
    @BindView(R.id.add_book_review_field) TextInputLayout reviewField;
    @BindView(R.id.add_book_purchase_dt_field) TextInputLayout purchaseDateField;
    @BindView(R.id.add_book_purchase_price_field) TextInputLayout purchasPriceField;
    @BindView(R.id.add_book_title_data) EditText title;
    @BindView(R.id.add_book_author_data) AutoCompleteTextView author;
    @BindView(R.id.add_book_description_data) EditText description;
    @BindView(R.id.add_book_genre_data) AutoCompleteTextView genre;
    @BindView(R.id.cover_image) ImageView coverImage;
    @BindView(R.id.add_book_review_data) EditText review;
    @BindView(R.id.add_book_purchase_date_data) EditText purchaseDate;
    @BindView(R.id.add_book_purchase_price_data) EditText purchasePrice;
    @BindView(R.id.add_book_rating_data) RatingBar rating;
    @BindView(R.id.camera_fab) FloatingActionButton cameraFab;

    public interface AddBookCallback{
        void inProgress();
        void onSuccess();
        void onFailure();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = Repository.getRepository();
        authors = repository.getAuthors();
        genres = repository.getGenres();

        Intent intent = getIntent();
        bookToEdit = intent.getParcelableExtra("Book");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        setContentView(R.layout.fragment_edit_book);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        toolbarTitle.setText("Add New Book");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ButterKnife.bind(this);
        if(bookToEdit != null){
            populateDetails();
        }

        setupAuthorAutocomplete();
        setupGenreAutoComplete();

        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeBookImage();
            }
        });

    }



    private void populateDetails(){
        title.setText(bookToEdit.getTitle());
        author.setText(bookToEdit.getAuthor().getName());
        description.setText(bookToEdit.getDescription());
        genre.setText(bookToEdit.getGenre().getGenreName());
        File file = null;
        if(bookToEdit.getCoverImage() != null){
            file = new File(bookToEdit.getCoverImage());
        }

        Picasso.with(this)
                .load(file)
                .placeholder(R.drawable.book_default)
                .error(R.drawable.book_default)
                .into(coverImage);

        Review reviewContent = book.getReview();
        if(reviewContent != null) {
            review.setText(reviewContent.getReviewContent());
        }
        purchaseDate.setText(bookToEdit.getPurchaseDateString());
        purchasePrice.setText(String.valueOf(bookToEdit.getPurchasePrice()));
        rating.setRating(bookToEdit.getRating());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_book_menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean mandatoryDetailsComplete = true;

        if(title.getText().toString().trim().isEmpty()){
            titleField.setError("Title is required!");
            mandatoryDetailsComplete = false;
        }

        if(author.getText().toString().trim().isEmpty()){
            authorField.setError("Author is required!");
            mandatoryDetailsComplete = false;
        }

        if(description.getText().toString().trim().isEmpty()){
            descrField.setError("Description is required!");
            mandatoryDetailsComplete = false;
        }

        if(genre.getText().toString().trim().isEmpty()){
            genreField.setError("Genre is required!");
            mandatoryDetailsComplete = false;
        }

        if(mandatoryDetailsComplete){

            if(bookToEdit == null){
                book.setTitle(title.getText().toString());

                // Check if Author exists first.
                String inputAuthor = author.getText().toString();
                Author authorEntity =  null;
                for(Author author : authors) {
                    if(author.getName().equalsIgnoreCase(inputAuthor)) {
                        authorEntity = author;
                    }
                }
                if(authorEntity != null) {
                    book.setAuthor(authorEntity);
                } else {
                    book.setAuthor(new Author(inputAuthor));
                }

                // Check if Genre exists first.
                String inputGenre = genre.getText().toString();
                Genre genreEntity = null;
                for(Genre genre : genres) {
                    if(genre.getGenreName().equalsIgnoreCase(inputGenre)) {
                        genreEntity = genre;
                    }
                }
                if(genreEntity != null){
                    book.setGenre(genreEntity);
                } else {
                    book.setGenre(new Genre(inputGenre));
                }

                book.setDescription(description.getText().toString());
                book.setCoverImage(SaveImageHelper.saveImageToInternalStorage(imageToStore, book));
                book.setReview(new Review(Calendar.getInstance(), review.getText().toString()));
                book.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
                book.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
                book.setRating(((int)rating.getRating()));

                repository.insertBook(book, new AddBookCallback() {
                    @Override
                    public void inProgress() {
                        // TODO: Display loading spinner.
                        showSnackBar("Inserting book...");
                    }

                    @Override
                    public void onSuccess() {
                        showSnackBar("Book inserted successfully...");
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        // TODO: Display error and log to crash reporting.
                        showSnackBar("Book insert failed...");
                    }
                });
                return true;
            } else{
                bookToEdit.setTitle(title.getText().toString());
                bookToEdit.setAuthor(new Author(author.getText().toString()));
                bookToEdit.setGenre(new Genre(genre.getText().toString()));
                bookToEdit.setDescription(description.getText().toString());
                bookToEdit.setGenre(new Genre(genre.getText().toString()));
                bookToEdit.setCoverImage(SaveImageHelper.saveImageToInternalStorage(imageToStore, book));
                bookToEdit.setReview(new Review(Calendar.getInstance(), review.getText().toString()));
                bookToEdit.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
                bookToEdit.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
                bookToEdit.setRating(((int)rating.getRating()));

                repository.updateBook(bookToEdit, new AddBookCallback() {
                    @Override
                    public void inProgress() {
                        // TODO: Display loading spinner.
                        showSnackBar("Updating book...");
                    }

                    @Override
                    public void onSuccess() {
                        showSnackBar("Book Updating successfully...");
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        // TODO: Display error and log to crash reporting.
                        showSnackBar("Book Updating failed...");
                    }
                });
                return true;
            }

        } else{
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

    private void setupAuthorAutocomplete(){

        final List<String> AUTHORS = repository.getAuthorNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, AUTHORS);


        author.setAdapter(adapter);
    }

    private void setupGenreAutoComplete(){

        final List<String> GENRE = repository.getGenreNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, GENRE);

        genre.setAdapter(adapter);

    }

    private void takeBookImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, IMAGE_CAPTURE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_CAPTURE_REQUEST && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            coverImage.setImageBitmap(image);
            setImageToStore(image);
        }
    }

}
