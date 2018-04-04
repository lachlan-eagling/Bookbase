package com.bookbase.app.backup;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import com.bookbase.app.model.entity.Book;
import com.bookbase.app.model.repository.Repository;
import com.crashlytics.android.Crashlytics;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BackupService extends Activity{

    private final Moshi moshi;
    private Repository repository;
    private BackupService instance = null;
    public static final int WRITE_REQUEST_CODE = 43;
    private File downloadedBackup = null;

    private BackupService() {
        moshi = new Moshi.Builder().add(new BookBackupAdapter()).build();
        repository = Repository.getRepository();
    }

    public BackupService getInstance() {
        if (instance == null) {
            instance = new BackupService();
        }
        return instance;
    }

    public void executeBackup(BackupCallback callback) {
        callback.inProgress();
        List<Book> books = Repository.getRepository().getBookList();
        File backup = createBackup(books);
        if (backup != null) {
            Intent intent = new Intent(Intent.ACTION_SEND);

            if (backup.exists() && backup.length() != 0) {
                Uri backupUri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".app.utils", backup);
                intent.setType("text/plain");
                //Uri backupUri = Uri.parse("content:/" + backup.getAbsolutePath());
                intent.putExtra(Intent.EXTRA_STREAM, backupUri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Save Bookbase Backup");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Bookbase Backup"));

                callback.onComplete(backup);
            } else {
                callback.onError();
            }

        }
    }

    public void restoreBackup() {
        String backup = backupToString(fetchSavedBackup());
        Type type = Types.newParameterizedType(List.class, Book.class);
        JsonAdapter<List<Book>> adapter = moshi.adapter(type);
        List<Book> books = new ArrayList<>();
        try {
            books.addAll(adapter.fromJson(backup));
        } catch (IOException e) {
            Crashlytics.logException(e);
        }

        if (!books.isEmpty()) {
            repository.insertBookList(books);
        }

    }

    private File createBackup(List<Book> books) {
        Type type = Types.newParameterizedType(List.class, Book.class);
        JsonAdapter<List<Book>> adapter = moshi.adapter(type);
        String json = adapter.toJson(books);
        File outDir = this.getExternalCacheDir();
        File backup = new File(outDir, "Bookbase.backup");

        try {
            OutputStream stream = new FileOutputStream(backup.getAbsolutePath());
            stream.write(json.getBytes());
            stream.close();
        } catch (FileNotFoundException e) {
            Crashlytics.logException(e);
        } catch (IOException e) {
            Crashlytics.logException(e);
        }
        return backup;
    }

    private File fetchSavedBackup() {
        // Import saved backup file provided by user.
        String fileName = this.getExternalCacheDir().getAbsolutePath() + "Bookbase.backup";
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);

        if(downloadedBackup != null & downloadedBackup.length() > 0) {
            return downloadedBackup;
        }

        // Return an empty file if nothing returned from select file activity.
        return new File(fileName);
    }


    private void scheduleBackup() {
    }

    private String backupToString(File file) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream stream = new FileInputStream(file.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line = reader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            Crashlytics.logException(e);
        } catch (IOException e) {
            Crashlytics.logException(e);
        }
        return sb.toString();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (resultData != null) {
                Uri uri = resultData.getData();
                downloadedBackup = new File(uri.getPath());
            }
        }
    }


}
