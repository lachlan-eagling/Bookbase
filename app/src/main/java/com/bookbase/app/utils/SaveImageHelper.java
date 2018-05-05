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
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SaveImageHelper {


    public static String saveImageToInternalStorage(Bitmap image, Book book){
        if(image != null){
            ContextWrapper context = new ContextWrapper(HomeScreen.getContext());

            // Setup date format and get current datetime to append to filename.
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm", Locale.ENGLISH);
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
            return myPath.getAbsolutePath();
        }

        return null;
    }

}
