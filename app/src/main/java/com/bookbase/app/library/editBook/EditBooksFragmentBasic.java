package com.bookbase.app.library.editBook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bookbase.app.R;


public class EditBooksFragmentBasic extends Fragment {

    private String title;
    private int page;

    private ImageView coverImage;



    public static EditBooksFragmentBasic newInstance(int page, String title){
        EditBooksFragmentBasic addBooksFragmentBasic = new EditBooksFragmentBasic();
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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
