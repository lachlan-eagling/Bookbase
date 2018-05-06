package com.bookbase.app.library;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.library.addBook.AddBookActivity;
import com.bookbase.app.library.viewBook.ViewBookFragment;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.repository.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BooksFragment extends Fragment implements Runnable, android.support.v7.widget.SearchView.OnQueryTextListener {

    private List<Book> books;
    private RecyclerView bookList;
    private Repository repository;
    private TextView emptyView;
    BooksAdapter adapter;

    private final Comparator<Book> comparator = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };

    public BooksFragment() {}

    public static BooksFragment newInstance() {
        return new BooksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = Repository.getRepository(getActivity());
        books = repository.getBookList();
    }



    @Override
    public void onResume() {
        super.onResume();
        books = repository.getBookList();
        setupAdapter(books);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.library_menu, menu);

        final TextView toolbarTitle = getActivity().findViewById(R.id.toolbar_title);
        final MenuItem searchMenuItem = menu.findItem(R.id.searchButton);
        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchMenuItem);
        searchView.setOnQueryTextListener(this);

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarTitle.setVisibility(View.GONE);
            }
        });

        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                toolbarTitle.setVisibility(View.VISIBLE  );
                return false;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.searchButton:
                TransitionManager.beginDelayedTransition((ViewGroup) getActivity().findViewById(R.id.toolbar));
                MenuItemCompat.expandActionView(item);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Book> filteredModelList = filter(books, newText);
        adapter.replaceAll(filteredModelList);
        bookList.scrollToPosition(0);
        return true;
    }

    private static List<Book> filter(List<Book> books, String query) {
        final String lowerCaseQuery = query.toLowerCase();

        final List<Book> filteredBookList = new ArrayList<>();
        for (Book book : books) {
            final String text = book.getTitle().toLowerCase();
            if (text.contains(lowerCaseQuery)) {
                filteredBookList.add(book);
            }
        }
        return filteredBookList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        bookList = view.findViewById(R.id.books_list);
        emptyView = view.findViewById(R.id.empty_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        bookList.setLayoutManager(layoutManager);
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
                    bundle.putInt("bookId", ((BooksAdapter) bookList.getAdapter()).getBookIdAt(position));
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
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
    public void run(){
        AppDatabase database = AppDatabase.getDatabase(this.getContext());
    }


    private void setupAdapter(List<Book> books){
        if (adapter == null) {
            adapter = new BooksAdapter(getActivity(), books, comparator);
        }
        adapter.add(books);
        if(books.isEmpty()) {
            bookList.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            emptyView.getRootView().setBackgroundColor(Color.WHITE);
        } else {
            bookList.setVisibility(View.VISIBLE);
            bookList.setAdapter(adapter);
            int currSize = adapter.getItemCount();
            adapter.notifyItemRangeInserted(currSize, books.size());
            adapter.notifyDataSetChanged();
        }
    }

}
