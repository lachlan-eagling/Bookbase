package com.bookbase.bookbase.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookbase.bookbase.R;
import com.bookbase.bookbase.adapters.BooksAdapter;
import com.bookbase.bookbase.database.AppDatabase;
import com.bookbase.bookbase.database.DatabaseFactory;
import com.bookbase.bookbase.model.entity.Book;

import java.util.List;

public class BooksFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Book> books;
    private AppDatabase database;
    private RecyclerView bookList;

    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance(String param1, String param2) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        bookList = (RecyclerView) getView().findViewById(R.id.books_list);
//        books = database.bookDao().getBooks();

//        BooksAdapter adapter = new BooksAdapter(this.getContext(), books);
//        bookList.setAdapter(adapter);
//        bookList.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        bookList = (RecyclerView) view.findViewById(R.id.books_list);

        books = database.bookDao().getBooks();
        BooksAdapter adapter = new BooksAdapter(this.getContext(), books);

        bookList.setAdapter(adapter);
        bookList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            database = DatabaseFactory.getDb(this.getContext());
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
