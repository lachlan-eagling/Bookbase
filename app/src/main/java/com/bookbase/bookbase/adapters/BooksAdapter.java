package com.bookbase.bookbase.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bookbase.bookbase.R;
import com.bookbase.bookbase.model.entity.Book;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView coverImage;
        public TextView title;
        public TextView author;

        public ViewHolder(View view){
            super(view);

            coverImage = (ImageView) view.findViewById(R.id.book_list_image);
            title = (TextView) view.findViewById(R.id.book_list_title);
            author = (TextView) view.findViewById(R.id.book_list_author);
        }

    }

    private List<Book> books;
    private Context context;

    public BooksAdapter(Context context, List<Book> books){
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

    @TargetApi(21)
    public void onBindViewHolder(BooksAdapter.ViewHolder viewHolder, int position){
        // Get reference to item at current position.
        Book book = books.get(position);

        // Reference layout componenets.
        ImageView coverImage;
        TextView title;
        TextView author;
        coverImage = viewHolder.coverImage;
        title = viewHolder.title;
        author = viewHolder.author;

        // Set view content from model.
        Drawable img = getContext().getApplicationContext().getDrawable(R.drawable.dummy_book);
        coverImage.setImageDrawable(img);
        title.setText(book.getTitle());
        author.setText(book.authorName());
    }

    @Override
    public int getItemCount(){
        return books.size();
    }



}
