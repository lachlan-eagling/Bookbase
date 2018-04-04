package com.bookbase.app.backup;

import com.bookbase.app.model.entity.Author;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.entity.Genre;
import com.bookbase.app.model.entity.Review;
import com.squareup.moshi.FromJson;
import com.squareup.moshi.ToJson;

import java.util.Calendar;

public class BookBackupAdapter {

    @ToJson BookBackup toJson(Book book) {
        final BookBackup out = new BookBackup();
        final Author author = book.getAuthor();
        final Genre genre = book.getGenre();
        final Review review = book.getReview();

        out.setBookId(book.getBookId());
        out.setBookTitle(book.getTitle());
        out.setDescription(book.getDescription());
        out.setCoverImage(book.getCoverImage());
        out.setIsbn(book.getIsbn());
        out.setRating(book.getRating());
        out.setRead(book.getIsRead());
        out.setPurchaseDate(book.getPurchaseDate().getTimeInMillis());
        out.setPurchasePrice(book.getPurchasePrice());
        out.setAuthorId(author.getAuthorId());
        out.setAuthorName(author.getName());
        out.setGenreId(genre.getGenreId());
        out.setGenreName(genre.getGenreName());
        out.setReviewId(review.getReviewId());
        out.setReviewDate(review.getReviewDate().getTimeInMillis());
        out.setReviewContent(review.getReviewContent());

        return out;
    }

    @FromJson Book fromJson(BookBackup in) {
        final Book book = new Book();
        book.setBookId(in.getBookId());
        book.setTitle(in.getBookTitle());
        book.setDescription(in.getDescription());
        book.setCoverImage(in.getCoverImage());
        book.setIsbn(in.getIsbn());
        book.setRating(in.getRating());
        book.setIsRead(in.isRead());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(in.getPurchaseDate());
        book.setPurchaseDate(calendar);
        book.setPurchasePrice(in.getPurchasePrice());

        Author author = new Author();
        author.setAuthorId(in.getAuthorId());
        author.setName(in.getAuthorName());

        Genre genre = new Genre();
        genre.setGenreId(in.getGenreId());
        genre.setGenreName(in.getGenreName());

        Review review = new Review();
        review.setReviewId(in.getReviewId());
        calendar.setTimeInMillis(in.getReviewDate());
        review.setReviewDate(calendar);
        review.setReviewContent(in.getReviewContent());

        book.setAuthor(author);
        book.setGenre(genre);
        book.setReview(review);

        return book;
    }
}
