package com.bookbase.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.bookbase.app.R;


public class AddBooksFragmentBasic extends Fragment {

    private String title;
    private int page;



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
        return inflater.inflate(R.layout.fragment_add_book_basic, container, false);
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

    }
}
