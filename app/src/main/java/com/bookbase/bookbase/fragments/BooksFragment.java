package com.bookbase.bookbase.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment implements Runnable{

    private OnFragmentInteractionListener mListener;
    private ArrayList<Book> books;
    private AppDatabase database;
    private RecyclerView bookList;
    private BooksAdapter adapter;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            run();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queryBookData qry = new queryBookData();
        qry.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        bookList = (RecyclerView) view.findViewById(R.id.books_list);
        bookList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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

    @Override
    public void run(){
        database = DatabaseFactory.getDb(this.getContext());
    }


    // Query books on background thread and post results back to main thread.
    private class queryBookData extends AsyncTask<Void, Void, List<Book>>{

        @Override
        protected List<Book> doInBackground(Void... v) {
            return database.bookDao().getBooks();
        }

        @Override
        protected void onPostExecute(List<Book> books){
            super.onPostExecute(books);
            adapter = new BooksAdapter(getActivity(), books);
            bookList.setAdapter(adapter);
            int currSize = adapter.getItemCount();
            adapter.notifyItemRangeInserted(currSize, books.size());
        }
    }
}
