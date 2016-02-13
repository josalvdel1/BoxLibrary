package com.josalvdel1.boxlibrary.data.task;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxFiles;
import com.josalvdel1.boxlibrary.BoxLibraryApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class DownloadFilesTask extends AsyncTask<List<DbxFiles.Metadata>, Integer, Boolean> {

    DbxClientV2 client;
    Application application;
    Callback callback;

    @Inject
    public DownloadFilesTask(BoxLibraryApplication application, DbxClientV2 dbxClientV2) {
        this.client = dbxClientV2;
        this.application = application;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback == null) {
            throw new IllegalStateException("Callback should not be null");
        }
    }

    @Override
    protected Boolean doInBackground(List<DbxFiles.Metadata>... filesData) {
        for (DbxFiles.Metadata fileData : filesData[0]) {
            try {
                File dir = application.getExternalFilesDir("books");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File file = new File(dir.getPath(), fileData.name);

                client.files.downloadBuilder(fileData.pathLower).run(new FileOutputStream(file));
                Log.d("DownloadFileTask", "File " + fileData.name + " downloaded");

            } catch (DbxException | IOException e) {
                Log.e(e.getClass().getName(), e.getMessage());
            }
        }
        return true;
    }

    public DownloadFilesTask setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public interface Callback {
        void onDownloadsComplete();

        void onError();
    }
}
