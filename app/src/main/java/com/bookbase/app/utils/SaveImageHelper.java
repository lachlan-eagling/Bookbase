package com.bookbase.app.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.bookbase.app.mainscreen.HomeScreen;
import com.bookbase.app.model.entity.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SaveImageHelper {

    // Temporarily store image when it results from a network call.
    private static Bitmap temp = null;

    public static String saveImageToInternalStorage(Bitmap image, Book book){
        if(image != null){
            ContextWrapper context = new ContextWrapper(HomeScreen.getContext());

            // Setup date format and get current datetime to append to filename.
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
            Date now = new Date();

            // Setup directory, file and output stream.
            File dir = context.getDir("images", Context.MODE_PRIVATE);
            File myPath = new File(dir, book.getTitle() + "_coverimage_" + df.format(now) + ".bmp");
            FileOutputStream out = null;

            try{
                out = new FileOutputStream(myPath);
                image.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch(IOException e){
                e.printStackTrace();
            } finally{
                try{
                    out.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            setTemp(null);
            return myPath.getAbsolutePath();
        }

        return null;
    }

    public static String saveImageToInternalStorage(String url, final Book book){

        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] imageBytes = response.body().bytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                setTemp(bitmap);

            }
        };

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);

        return saveImageToInternalStorage(temp, book);
    }

    private static void setTemp(Bitmap img){
        temp = img;
    }

}
