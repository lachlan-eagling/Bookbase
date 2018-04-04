package com.bookbase.app.backup;

import java.io.File;

public interface BackupCallback {

    void inProgress();
    void onComplete(File backup);
    void onError();

}
