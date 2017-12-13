package com.bookbase.app.book.addBook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import com.bookbase.app.R;


public class AddBooksFragmentBasic extends Fragment {

    private String title;
    private int page;

    private FloatingActionButton cameraFab;
    private ImageView coverImage;
    private String storedImageReference;
    public static final int IMAGE_CAPTURE_REQUEST = 1;



    public static AddBooksFragmentBasic newInstance(int page, String title){
        AddBooksFragmentBasic addBooksFragmentBasic = new AddBooksFragmentBasic();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        addBooksFragmentBasic.setArguments(args);
        return addBooksFragmentBasic;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // TODO: Finish implementation of fragment_add_book_basic layout.
        View view = inflater.inflate(R.layout.fragment_add_book_basic, container, false);
        coverImage = (ImageView) view.findViewById(R.id.cover_image);
        cameraFab = (FloatingActionButton) view.findViewById(R.id.camera_fab);
        cameraFab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                takeBookImage();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupGenreAutoComplete();
        setupAuthorAutocomplete();
    }

    private void setupAuthorAutocomplete(){
        // Temp implementation to test auto complete.
        final String[] AUTHORS = new String[] {
                "George R.R Martin", "George Lucas", "Beatrix Potter", "Neil Gaimen", "J.K. Rowling", "J.R.R Tolkein"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, AUTHORS);

        AutoCompleteTextView textView = getView().findViewById(R.id.add_book_author_data);
        textView.setAdapter(adapter);

    }

    private void setupGenreAutoComplete(){
        // Temp implementation to test auto complete.
        final String[] GENRE = new String[] {
                "Fantasy", "Sci-fi", "Romance", "Biography", "Drama", "Horror"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, GENRE);

        AutoCompleteTextView textView = getView().findViewById(R.id.add_book_genre_data);
        textView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();

    }

    private void takeBookImage(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivityForResult(intent, IMAGE_CAPTURE_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == IMAGE_CAPTURE_REQUEST && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap image = (Bitmap) extras.get("data");
            coverImage.setImageBitmap(image);
            ((AddBookActivity)getActivity()).setImageToStore(image);
        }
    }
}
