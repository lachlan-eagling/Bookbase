package com.bookbase.app.model.api;

import android.support.annotation.NonNull;

import com.bookbase.app.BuildConfig;
import com.bookbase.app.model.entity.Book;
import com.crashlytics.android.Crashlytics;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class GoogleBooksApi {

    private static OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String GOOGLE_BOOKS_API_KEY = String.format("&key=%s", BuildConfig.GOOGLE_BOOKS_API_KEY);
    private static final String FILTERS = "&fields=items(volumeInfo/title,volumeInfo/authors,volumeInfo/description," +
            "volumeInfo/averageRating,volumeInfo/imageLinks/thumbnail)";

    private GoogleBooksApi(){}

    private enum SearchType {
        TITLE ("intitle:"),
        ISBN ("isbn:");

        private String queryParam;

        SearchType(String type){
            this.queryParam = type;
        }

        public String queryParam(){
            return queryParam;
        }
    }

    private static void query(String endpoint, UUID requestId, Callback callback){
        Request request  = new Request.Builder().url(endpoint).tag(requestId).build();
        client.newCall(request).enqueue(callback);
    }

    public static void queryByIsbn(String isbn, final UUID requestId, final BooksApiCallback booksApiCallback){

        booksApiCallback.inProgress();
        String url = buildEndpointUrl(SearchType.ISBN, isbn);

        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                booksApiCallback.onError();
                // TODO: Log to crash reporting.
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                booksApiCallback.onComplete(jsonToBookCollection(response));
            }
        };
        query(url, requestId, callback);
    }

    public static void queryByTitle(String title, UUID requestId, final BooksApiCallback booksApiCallback){
        booksApiCallback.inProgress();
        String url = buildEndpointUrl(SearchType.TITLE, title);
        Callback callback = new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                booksApiCallback.onError();
                // TODO: Log to crash reporting.
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                List<Book> books = jsonToBookCollection(response);
                if(books.isEmpty()){
                    booksApiCallback.onError();
                } else{
                    booksApiCallback.onComplete(books);
                }
            }
        };
        query(url, requestId, callback);

    }


    public static void cancelRequest(UUID requestId){
        for(Call call : client.dispatcher().queuedCalls()) {
            if(call.request().tag().equals(requestId))
                call.cancel();
        }
    }

    private static String buildEndpointUrl(SearchType searchType, String searchTerm){
        return API_URL + searchType.queryParam() + searchTerm + GOOGLE_BOOKS_API_KEY + FILTERS;
    }

    @SuppressWarnings("ConstantConditions")
    private static List<Book> jsonToBookCollection(Response response){
        String responseBody;
        JSONObject json;
        JSONArray jsonArray;

        Moshi moshi = new Moshi.Builder().add(new BookJsonAdapter()).build();
        Type type = Types.newParameterizedType(List.class, Book.class);
        JsonAdapter<List<Book>> jsonAdapter = moshi.adapter(type);
        List<Book> books = null;

        try{
            // Convert response body to JSON Array.
            responseBody = response.body().string();
            if(isResponseValid(responseBody)){
                json = new JSONObject(responseBody);
                jsonArray = json.getJSONArray("items");
                books = jsonAdapter.fromJson(jsonArray.toString());
            } else{
                // TODO: Log to crash reporting.
                return Collections.emptyList();
            }

        } catch (IOException e){
            Crashlytics.logException(e);
        } catch(JSONException e){
            Crashlytics.logException(e);
        }

        return books;
    }

    private static boolean isResponseValid(String response){
        return response.contains("items");
    }

}
