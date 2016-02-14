package com.josalvdel1.boxlibrary.data.task;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxFiles;
import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.data.NetworkManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

public class DownloadFilesTask extends AsyncTask<List<DbxFiles.Metadata>, Integer, Boolean> {

    private NetworkManager networkManager;
    private DbxClientV2 client;
    private Application application;
    private Callback callback;

    @Inject
    public DownloadFilesTask(BoxLibraryApplication application, DbxClientV2 dbxClientV2, NetworkManager networkManager) {
        this.client = dbxClientV2;
        this.application = application;
        this.networkManager = networkManager;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback == null) {
            throw new IllegalStateException("Callback should not be null");
        }
        if (!networkManager.hasNetworkConnection()) {
            cancel(true);
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
                if (!file.exists()) {
                    client.files.downloadBuilder(fileData.pathLower).run(new FileOutputStream(file));
                    Log.d("DownloadFileTask", "File " + fileData.name + " downloaded");
                }
            } catch (DbxException | IOException e) {
                Log.e(e.getClass().getName(), e.getMessage());
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        callback.onDownloadsComplete();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onError();
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
