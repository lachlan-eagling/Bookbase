package com.bookbase.app.model.api;

import android.util.Log;

import com.bookbase.app.model.entity.Book;
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
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class GoogleBooksApi {

    private static OkHttpClient client = new OkHttpClient();
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String API_KEY = String.format("&key=%s", ""); // Need to pass API key in here at build DO NOT commit to GitHub.
private static final String FILTERS = "&fields=items(volumeInfo/title,volumeInfo/authors,volumeInfo/description,volumeInfo/averageRating,volumeInfo/imageLinks/thumbnail)";

    private GoogleBooksApi(){
        // Prevent class from being instantiated.
    }

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

        String url = buildEndpointUrl(SearchType.ISBN, isbn);

        Log.d("Request URL: ", url);
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                booksApiCallback.onError();
                // TODO: Log to crash reporting.
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                jsonToBookCollection(response);
                booksApiCallback.onComplete(jsonToBookCollection(response));
            }
        };
        booksApiCallback.inProgress();
        query(url, requestId, callback);
    }

    public static List<Book> queryByTitle(String title, UUID requestId, BooksApiCallback callback){
        String url = buildEndpointUrl(SearchType.TITLE, title);
        return Collections.emptyList();
    }


    public static void cancelRequest(UUID requestId){
        for(Call call : client.dispatcher().queuedCalls()) {
            if(call.request().tag().equals(requestId))
                call.cancel();
        }
    }

    private static String buildEndpointUrl(SearchType searchType, String searchTerm){
        return API_URL + searchType.queryParam() + searchTerm + API_KEY + FILTERS;
    }

    private static List<Book> jsonToBookCollection(Response response){
        String responseBody;
        JSONObject json;
        JSONArray jsonArray = null;

        Moshi moshi = new Moshi.Builder().add(new BookJsonAdapter()).build();
        Type type = Types.newParameterizedType(List.class, BookJson.class);
        JsonAdapter<List<BookJson>> jsonAdapter = moshi.adapter(type);
        List<BookJson> books = null;

        try{
            // Convert response body to JSON Array.
            responseBody = response.body().string();
            json = new JSONObject(responseBody);
            jsonArray = json.getJSONArray("items");

            // Parse and convert JSON.
            books = jsonAdapter.fromJson(jsonArray.toString());
        } catch (IOException e){
            // Something went wrong...
        } catch(JSONException e){
            // Something went wrong...
        }

        // Logging for testing.
        Log.d("JSON Response", jsonArray.toString());
        if(books != null){
            Log.d("JSON Books", String.valueOf(books.get(0).toString()));
        } else{
            Log.d("JSON Books", "Books list is null");
        }

        return Collections.emptyList();
    }

}
