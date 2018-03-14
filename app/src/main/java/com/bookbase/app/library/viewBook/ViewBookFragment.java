package com.bookbase.app.library.viewBook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.library.addBook.AddBookActivity;
import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.repository.Repository;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewBookFragment extends Fragment {

    private Book book;
    private OnFragmentInteractionListener mListener;

    @BindView(R.id.view_book_title) TextView title;
    @BindView(R.id.view_book_author) TextView author;
    @BindView(R.id.view_book_rating) RatingBar rating;
    @BindView(R.id.view_book_cover) ImageView cover;
    @BindView(R.id.view_book_descr) TextView descr;
    @BindView(R.id.view_book_genre) TextView genre;
    @BindView(R.id.view_book_review) TextView review;
    @BindView(R.id.view_book_purchasedate) TextView purchaseDate;
    @BindView(R.id.view_book_purchaseprice) TextView purchasePrice;
    Toolbar toolbar;

    public ViewBookFragment() {
        // Required empty public constructor
    }

    public static ViewBookFragment newInstance(Bundle bundle) {
        ViewBookFragment fragment = new ViewBookFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
        Bundle bundle = this.getArguments();
        this.book = bundle.getParcelable("book");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.view_book_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        View menuActionItem = (getActivity()).findViewById(R.id.view_book_options);
        PopupMenu popupMenu = new PopupMenu(getActivity(), menuActionItem);
        popupMenu.getMenuInflater().inflate(R.menu.view_book_action_items, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                Repository repository = Repository.getRepository();
                switch((String) item.getTitle()){
                    case "Edit":
                        Intent intent = new Intent(getActivity(), AddBookActivity.class);
                        intent.putExtra("Book", book);
                        startActivity(intent);
                        break;
                    case "Delete":
                        repository.deleteBook(book);
                        getFragmentManager().popBackStack();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_book, container, false);
        ButterKnife.bind(this, view);

        AppCompatActivity activity = ((AppCompatActivity)getActivity());
        toolbar = activity.findViewById(R.id.toolbar); // Outside scope of fragments view so cannot bind with Butterknife.
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);


        title.setText(book.getTitle());
        author.setText(AppDatabase.getDatabase(HomeScreen.getContext()).authorDao().getAuthorById(book.getAuthor().getAuthorId()).getName());
        rating.setRating((float) book.getRating());
        descr.setText(book.getDescription());
        genre.setText(book.getGenre().getGenreName());
        review.setText(book.getReview().getReviewContent());
        purchaseDate.setText(book.getPurchaseDateString());
        purchasePrice.setText(String.valueOf(book.getPurchasePrice()));

        File file = null;
        if(book.getCoverImage() != null){
            file = new File(book.getCoverImage());
        }

        Picasso.with(this.getContext())
                .load(file)
                .placeholder(R.drawable.book_default)
                .error(R.drawable.book_default)
                .into(cover);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO: Need to update book in this view when returning to fragment from edit screen.
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
