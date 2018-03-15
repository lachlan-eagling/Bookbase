package com.bookbase.app.library;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.library.addBook.AddBookActivity;
import com.bookbase.app.library.viewBook.ViewBookFragment;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.repository.Repository;

import java.util.List;

public class BooksFragment extends Fragment implements Runnable{

    private OnFragmentInteractionListener mListener;
    private List<Book> books;
    private AppDatabase database;
    private RecyclerView bookList;
    private Repository repository;

    public interface OnFragmentInteractionListener { void onFragmentInteraction(Uri uri); }

    public BooksFragment() {}

    public static BooksFragment newInstance(String param1, String param2) {
        return new BooksFragment();
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
        repository = Repository.getRepository();
        books = repository.getBookList();
    }

    @Override
    public void onResume() {
        super.onResume();
        books = repository.getBookList();
        setupAdapter(books);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_books, container, false);
        bookList = view.findViewById(R.id.books_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(bookList.getContext(), layoutManager.getOrientation());
        bookList.setLayoutManager(layoutManager);
        //bookList.addItemDecoration(dividerItemDecoration);
        FloatingActionButton fab = view.findViewById(R.id.add_book_fab);
        setupAdapter(books);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                startActivity(intent);
            }
        });

        bookList.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), bookList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                try {
                    Fragment fragment = (ViewBookFragment.class).newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("book", books.get(position));
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content_frame, fragment)
                            .addToBackStack(null)
                            .commit();
                } catch(IllegalAccessException e){
                    e.printStackTrace();
                } catch(java.lang.InstantiationException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Snackbar.make(view, "Long touch", Snackbar.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void run(){ database = AppDatabase.getDatabase(this.getContext()); }


    private void setupAdapter(List<Book> books){
        BooksAdapter adapter;
        adapter = new BooksAdapter(getActivity(), books);
        bookList.setAdapter(adapter);
        int currSize = adapter.getItemCount();
        adapter.notifyItemRangeInserted(currSize, books.size());
        adapter.notifyDataSetChanged();
    }

}
