package com.bookbase.app.library.addBook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bookbase.app.R;
import com.bookbase.app.model.api.BooksApiCallback;
import com.bookbase.app.model.api.GoogleBooksApi;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.bookbase.app.model.repository.Repository;
import com.bookbase.app.utils.SaveImageHelper;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBookActivity extends AppCompatActivity {

    private Bitmap imageToStore;
    private File barcodeTemp;
    private String barcodeImagePath;
    final Book book = new Book();
    Book bookToEdit = null;
    boolean bookFromApiCall = false;
    private Repository repository;
    public static final int COVER_IMAGE_REQUEST = 1;
    public static final int BARCODE_IMAGE_REQUEST = 2;
    private SaveImageHelper imageHelper;

    private List<Author> authors;
    private List<Genre> genres;

    @BindView(R.id.add_book_title_field)
    TextInputLayout titleField;
    @BindView(R.id.add_book_author_field)
    TextInputLayout authorField;
    @BindView(R.id.add_book_descr_field)
    TextInputLayout descrField;
    @BindView(R.id.add_book_genre_field)
    TextInputLayout genreField;
    @BindView(R.id.add_book_review_field)
    TextInputLayout reviewField;
    @BindView(R.id.add_book_purchase_dt_field)
    TextInputLayout purchaseDateField;
    @BindView(R.id.add_book_purchase_price_field)
    TextInputLayout purchasPriceField;
    @BindView(R.id.add_book_title_data)
    EditText title;
    @BindView(R.id.add_book_author_data)
    AutoCompleteTextView author;
    @BindView(R.id.add_book_description_data)
    EditText description;
    @BindView(R.id.add_book_genre_data)
    AutoCompleteTextView genre;
    @BindView(R.id.cover_image)
    ImageView coverImage;
    @BindView(R.id.no_image)
    TextView lblNoImage;
    @BindView(R.id.add_book_review_data)
    EditText review;
    @BindView(R.id.add_book_purchase_date_data)
    EditText purchaseDate;
    @BindView(R.id.add_book_purchase_price_data)
    EditText purchasePrice;
    @BindView(R.id.add_book_rating_data)
    RatingBar rating;
    @BindView(R.id.camera_fab)
    FloatingActionButton cameraFab;

    public interface AddBookCallback {
        void inProgress();

        void onSuccess();

        void onFailure();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = Repository.getRepository(this);
        authors = repository.getAuthors();
        genres = repository.getGenres();
        imageHelper = new SaveImageHelper(this);

        Intent intent = getIntent();
        bookToEdit = intent.getParcelableExtra("Book");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        setContentView(R.layout.fragment_edit_book);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.lbl_add_new_book);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ButterKnife.bind(this);
        if (bookToEdit != null) {
            populateDetails(this);
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


    void populateDetails(final Context context) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                title.setText(bookToEdit.getTitle());
                author.setText(bookToEdit.getAuthor().getName());
                description.setText(bookToEdit.getDescription());
                Genre genreEntity = bookToEdit.getGenre();
                if (genreEntity != null) {
                    genre.setText(genreEntity.getGenreName());
                } else {
                    genre.setText(R.string.lbl_unknown_genre);
                }


                File file = null;
                boolean fromWeb = false;
                if (bookToEdit.getCoverImage() != null) {
                    lblNoImage.setVisibility(View.GONE);
                    coverImage.setVisibility(View.VISIBLE);
                    if (bookToEdit.getCoverImage().contains("http://")) {
                        fromWeb = true;
                    } else {
                        file = new File(bookToEdit.getCoverImage());
                    }
                }

                if (fromWeb) {
                    Picasso.with(context)
                            .load(bookToEdit.getCoverImage())
                            .placeholder(R.mipmap.no_cover)
                            .error(R.mipmap.no_cover)
                            .into(coverImage);
                } else {
                    Picasso.with(context)
                            .load(file)
                            .placeholder(R.mipmap.no_cover)
                            .error(R.mipmap.no_cover)
                            .into(coverImage);
                }


                Review reviewContent = book.getReview();
                if (reviewContent != null) {
                    review.setText(reviewContent.getReviewContent());
                }
                purchaseDate.setText(bookToEdit.getPurchaseDateString());
                purchasePrice.setText(String.valueOf(bookToEdit.getPurchasePrice()));
                rating.setRating(bookToEdit.getRating());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_book_menu_options, menu);
        return true;
    }

    private Context getContext() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_barcode:
                scanBarcode();
                return true;
            case R.id.action_done:
                return saveBook();
            default:
                return false;
        }

    }

    private void scanBarcode() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            barcodeTemp = saveBarCodeImage();
            if (barcodeTemp != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        this.getApplicationContext().getPackageName() + ".app.utils",
                        barcodeTemp);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, BARCODE_IMAGE_REQUEST);
            }
        }


    }

    private File saveBarCodeImage() {
        // Create an image file name
        String imageFileName = "BarcodeTemp";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".bmp",         /* suffix */
                    storageDir      /* directory */
            );
        } catch (IOException e) {
            Crashlytics.logException(e);
        }

        // Save a file: path for use with ACTION_VIEW intents
        if (image != null) {
            barcodeImagePath = image.getAbsolutePath();
        }
        return image;
    }

    private void processBarcode(final Context context) {
        BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        Bitmap barcodeImage = BitmapFactory.decodeFile(barcodeImagePath);
        if (detector.isOperational() && barcodeImage != null) {
            Log.d("Barcode Processing", "Detector operational, processing barcode.");
            Frame frame = new Frame.Builder().setBitmap(barcodeImage).build();
            SparseArray<Barcode> barcodes = detector.detect(frame);
            Barcode code;
            if (barcodes.size() > 0) {
                code = barcodes.valueAt(0);
                showSnackBar("Barcode: " + code.rawValue);
                Log.d("Barcode Processing", "Returned Barcode: " + code.rawValue);

                UUID requestId = UUID.randomUUID();
                BooksApiCallback callback = new BooksApiCallback() {
                    @Override
                    public void onError() {
                        Log.d("Barcode Processing", "Something went wrong with API search.");
                    }

                    @Override
                    public void onComplete(List<Book> books) {
                        if (!books.isEmpty()) {
                            Log.d("Barcode Processing", "Book Returned from API");
                            bookToEdit = books.get(0);
                            bookFromApiCall = true;
                            populateDetails(context);
                        } else {
                            Log.d("Barcode Processing", "No book Returned from API");
                        }
                    }

                    @Override
                    public void inProgress() {
                        Log.d("Barcode Processing", "API search in progress");
                    }
                };

                GoogleBooksApi.queryByIsbn(code.rawValue, requestId, callback);
            } else {
                showSnackBar("No barcode detected.");
                Log.d("Barcode Processing", "No barcode returned.");
            }
        } else {
            Log.d("Barcode Processing", "Detector not operational.");
        }
        if (barcodeTemp != null) {
            if(!barcodeTemp.delete()) {
                Crashlytics.logException(new IOException("Cannot delete barcode temp image."));
            }
        }

    }

    private boolean saveBook() {
        boolean mandatoryDetailsComplete = true;

        if (title.getText().toString().trim().isEmpty()) {
            titleField.setError("Title is required!");
            mandatoryDetailsComplete = false;
        }

        if (author.getText().toString().trim().isEmpty()) {
            authorField.setError("Author is required!");
            mandatoryDetailsComplete = false;
        }

        if (description.getText().toString().trim().isEmpty()) {
            descrField.setError("Description is required!");
            mandatoryDetailsComplete = false;
        }

        if (genre.getText().toString().trim().isEmpty()) {
            genreField.setError("Genre is required!");
            mandatoryDetailsComplete = false;
        }

        if (mandatoryDetailsComplete) {

            if (bookToEdit == null) {
                book.setTitle(title.getText().toString());

                // Check if Author exists first.
                String inputAuthor = author.getText().toString();
                Author authorEntity = null;
                for (Author author : authors) {
                    if (author.getName().equalsIgnoreCase(inputAuthor)) {
                        authorEntity = author;
                    }
                }
                if (authorEntity != null) {
                    book.setAuthor(authorEntity);
                } else {
                    book.setAuthor(new Author(inputAuthor));
                }

                // Check if Genre exists first.
                String inputGenre = genre.getText().toString();
                Genre genreEntity = null;
                for (Genre genre : genres) {
                    if (genre.getGenreName().equalsIgnoreCase(inputGenre)) {
                        genreEntity = genre;
                    }
                }
                if (genreEntity != null) {
                    book.setGenre(genreEntity);
                } else {
                    book.setGenre(new Genre(inputGenre));
                }

                book.setDescription(description.getText().toString());
                book.setCoverImage(imageHelper.saveImageToInternalStorage(imageToStore, book));
                book.setReview(new Review(Calendar.getInstance(), review.getText().toString()));
                book.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
                book.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
                book.setRating(((int) rating.getRating()));

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
            } else {
                String newCoverImg = imageHelper.saveImageToInternalStorage(imageToStore, book);
                if(newCoverImg == null) {
                    newCoverImg = bookToEdit.getCoverImage();
                }
                bookToEdit.setTitle(title.getText().toString());
                bookToEdit.setAuthor(new Author(author.getText().toString()));
                bookToEdit.setGenre(new Genre(genre.getText().toString()));
                bookToEdit.setDescription(description.getText().toString());
                bookToEdit.setGenre(new Genre(genre.getText().toString()));
                bookToEdit.setCoverImage(newCoverImg);
                bookToEdit.setReview(new Review(Calendar.getInstance(), review.getText().toString()));
                bookToEdit.setPurchaseDate(parseDate(purchaseDate.getText().toString()));
                bookToEdit.setPurchasePrice(parseDouble(purchasePrice.getText().toString()));
                bookToEdit.setRating(((int) rating.getRating()));

                AddBookCallback callback = new AddBookCallback() {
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
                };

                if(bookFromApiCall) {
                    repository.insertBook(bookToEdit, callback);
                } else {
                    repository.updateBook(bookToEdit, callback);
                }
                imageToStore = null;
                return true;
            }

        } else {
            if (this.getCurrentFocus() != null) {
                Snackbar.make(this.getCurrentFocus(), "Missing mandatory fields!", Snackbar.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    // TODO: Helper method for testing, remove this.
    void showSnackBar(String message) {
        if (this.getCurrentFocus() != null) {
            Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
        }

    }

    private Calendar parseDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("DD/MM/YYY", Locale.ENGLISH);

        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return calendar;

    }

    private double parseDouble(String in) {
        try {
            return Double.parseDouble(in);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public void setImageToStore(Bitmap image) {
        imageToStore = image;
    }

    private void setupAuthorAutocomplete() {

        final List<String> AUTHORS = repository.getAuthorNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, AUTHORS);


        author.setAdapter(adapter);
    }

    private void setupGenreAutoComplete() {

        final List<String> GENRE = repository.getGenreNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, GENRE);

        genre.setAdapter(adapter);

    }

    void takeBookImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, COVER_IMAGE_REQUEST);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COVER_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image;
            if (extras != null) {
                image = (Bitmap) extras.get("data");
            }
            lblNoImage.setVisibility(View.GONE);
            coverImage.setVisibility(View.VISIBLE);
            coverImage.setImageBitmap(image);
            setImageToStore(image);
        } else if (requestCode == BARCODE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            processBarcode(this);
        }
    }

}
