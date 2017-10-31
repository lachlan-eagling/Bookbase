package com.bookbase.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
}
