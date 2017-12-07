package com.bookbase.app.book;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookbase.app.R;


public class AddBooksFragmentAdvanced extends Fragment {

    private String title;
    private int page;

    public static AddBooksFragmentAdvanced newInstance(int page, String title){
        AddBooksFragmentAdvanced addBooksFragmentBasic = new AddBooksFragmentAdvanced();
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
        // TODO: Finish implementation of fragment_add_book_advanced layout.
        View view = inflater.inflate(R.layout.fragment_add_book_advanced, container, false);
        return view;
    }
}
