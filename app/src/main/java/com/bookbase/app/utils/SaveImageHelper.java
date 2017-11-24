package com.bookbase.app.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;

import com.bookbase.app.activities.MainActivity;
import com.bookbase.app.model.entity.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveImageHelper {

    private static String saveImageToInternalStorage(Bitmap image, Book book){
        ContextWrapper context = new ContextWrapper(MainActivity.getContext());

        // Setup date format and get current datetime to append to filename.
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmm");
        Date now = new Date();

        // Setup directory, file and output stream.
        File dir = context.getDir("images", Context.MODE_PRIVATE);
        File myPath = new File(dir, book.getTitle() + "_coverimage_" + df.format(now));
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
        return dir.getAbsolutePath();

    }

}
