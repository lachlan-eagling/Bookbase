package com.bookbase.app.library.addBook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.bookbase.app.common.BaseActivity;
import com.bookbase.app.common.BasePresenter;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.crashlytics.android.Crashlytics;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBookActivity extends BaseActivity implements AddBookViewInterface{

    private Bitmap imageToStore;
    private String barcodeImagePath;
    private final Book book = new Book();
    private Book bookToEdit = null;
    private static final int COVER_IMAGE_REQUEST = 1;
    private static final int BARCODE_IMAGE_REQUEST = 2;
    private AddBookPresenter presenter;

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
    @BindView(R.id.no_image) TextView lblNoImage;
    @BindView(R.id.add_book_review_data) EditText review;
    @BindView(R.id.add_book_purchase_date_data) EditText purchaseDate;
    @BindView(R.id.add_book_purchase_price_data) EditText purchasePrice;
    @BindView(R.id.add_book_rating_data) RatingBar rating;
    @BindView(R.id.camera_fab) FloatingActionButton cameraFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ButterKnife.bind(this);
        //presenter = new AddBookPresenter(this);

        Intent intent = getIntent();
        bookToEdit = intent.getParcelableExtra("Book");
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.fragment_edit_book);

        presenter.setupAutoFillFields();
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        toolbarTitle.setText(R.string.lbl_add_new_book);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (bookToEdit != null) {
            populateDetails();
        }

        cameraFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeBookImage();
            }
        });
    }

    @Override
    protected BasePresenter attachPresenter() {
        return new AddBookPresenter(this);
    }

    private void populateDetails() {
        final Context context = this;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_barcode:
                scanBarcode();
                return true;
            case R.id.action_done:
                // Get data.
                String _title = title.getText().toString();
                String _author = author.getText().toString();
                String _description = description.getText().toString();
                String _genre = genre.getText().toString();
                String _review = review.getText().toString();
                String _purchaseDate  = purchaseDate.getText().toString();
                String _price = purchasePrice.getText().toString();
                float _rating = rating.getRating();

                // Delegate save out to presenter
                presenter.saveBook(bookToEdit.getBookId(), _title, _author, _description, _genre, _review,
                        _purchaseDate, _price, _rating, imageToStore, bookToEdit != null);
                return true;
            default:
                return false;
        }
    }

    private void scanBarcode() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Create file to save image result to.
            File barcodeTemp = null;
            String imageFileName = "BarcodeTemp";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            try {
                barcodeTemp = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".bmp",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                Crashlytics.logException(e);
            }

            // Save a file: path for use with ACTION_VIEW intents
            if (barcodeTemp != null) {
                barcodeImagePath = barcodeTemp.getAbsolutePath();
            }

            if (barcodeTemp != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        this.getApplicationContext().getPackageName() + ".app.utils",
                        barcodeTemp);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, BARCODE_IMAGE_REQUEST);
            }
        }


    }

    private void takeBookImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, COVER_IMAGE_REQUEST);
        }
    }

    private void setImageToStore(Bitmap image) {
        imageToStore = image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == COVER_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap image = null;
            if (extras != null) {
                image = (Bitmap) extras.get("data");
            }
            lblNoImage.setVisibility(View.GONE);
            coverImage.setVisibility(View.VISIBLE);
            if (image != null) {
                coverImage.setImageBitmap(image);
            }
            setImageToStore(image);
        } else if (requestCode == BARCODE_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            presenter.processBarcode(barcodeImagePath);
        }
    }

    @Override
    public void onBookSave() {
        finish();
    }

    @Override
    public void onBookSaveError(Throwable error) {
        Crashlytics.logException(error);
        // TODO: Notify user of error.
        finish();
    }

    @Override
    public void setupAuthorAutofill(final List<String> authorNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, authorNames);
        author.setAdapter(adapter);
    }

    @Override
    public void setupGenreAutofill(final List<String> genreNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, genreNames);
        genre.setAdapter(adapter);
    }

    @Override
    public void sendApiResponse(Book book) {
        bookToEdit = book;
        populateDetails();
    }

    @Override
    public void onBarcodeError(String error) {
        showSnackBar("Error processing barcode!");
    }

    @Override
    public void barcodeProcessing() {
        // TODO: Display loading spinner.
    }

    @Override
    public void titleInvalid() {
        titleField.setError("");
        showSnackBar("Missing mandatory fields");
    }

    @Override
    public void authorInvalid() {
        authorField.setError("");
        showSnackBar("Missing mandatory fields");
    }

    @Override
    public void descriptionInvalid() {
        descrField.setError("");
        showSnackBar("Missing mandatory fields");
    }

    @Override
    public void genreInvalid() {
        genreField.setError("");
        showSnackBar("Missing mandatory fields");
    }

    private void showSnackBar(String message) {
        if (this.getCurrentFocus() != null) {
            Snackbar.make(this.getCurrentFocus(), message, Snackbar.LENGTH_SHORT).show();
        }

    }
}
