package com.bookbase.app.library;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bookbase.app.R;
import com.bookbase.app.database.AppDatabase;
import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{

    class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.book_list_image) ImageView coverImage;
        @BindView(R.id.book_list_title) TextView title;
        @BindView(R.id.book_list_author) TextView author;
        @BindView(R.id.book_list_descr) TextView descr;
        @BindView(R.id.book_list_rating) RatingBar rating;

        ViewHolder(View view){
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    private List<Book> books;
    private Context context;

    BooksAdapter(Context context, List<Book> books){
        this.books = books;
        this.context = context;
    }

    private Context getContext(){
        return context;
    }

    @Override
    public BooksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bookView = inflater.inflate(R.layout.item_book, parent, false);
        return new ViewHolder(bookView);

    }

    public void onBindViewHolder(BooksAdapter.ViewHolder viewHolder, int position){

        Book book = books.get(position);
        File file = null;

        final AppDatabase db = AppDatabase.getDatabase(context);
        Author authorEntity = db.authorDao().getAuthorById(book.getAuthor().getAuthorId());

        ImageView coverImage = viewHolder.coverImage;
        TextView title = viewHolder.title;
        TextView author = viewHolder.author;
        TextView descr = viewHolder.descr;
        RatingBar rating = viewHolder.rating;
        if(book.getCoverImage() != null){
            file = new File(book.getCoverImage());
        }

        Picasso.with(this.getContext())
                .load(file)
                .placeholder(R.drawable.book_default)
                .error(R.drawable.book_default)
                .into(coverImage);
        title.setText(book.getTitle());
        author.setText(authorEntity.getName());
        descr.setText(book.getDescription());
        rating.setRating(book.getRating());

    }

    @Override
    public int getItemCount(){
        return books.size();
    }

}
