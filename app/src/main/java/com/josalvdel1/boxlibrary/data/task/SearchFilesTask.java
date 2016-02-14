package com.josalvdel1.boxlibrary.data.task;

import android.os.AsyncTask;
import android.util.Log;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxFiles;
import com.josalvdel1.boxlibrary.data.NetworkManager;

import javax.inject.Inject;

public class SearchFilesTask extends AsyncTask<String, Integer, DbxFiles.SearchResult> {

    private NetworkManager networkManager;
    private DbxClientV2 client;
    private Callback callback;

    @Inject
    public SearchFilesTask(DbxClientV2 client, NetworkManager networkManager) {
        this.client = client;
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
    protected DbxFiles.SearchResult doInBackground(String... strings) {
        DbxFiles.SearchResult searchResult = null;
        try {
            searchResult = client.files.search("", ".epub");
            searchResult.matches.size();
        } catch (DbxException e) {
            Log.e("DbException", e.getMessage());
        }
        return searchResult;
    }


    @Override
    protected void onPostExecute(DbxFiles.SearchResult searchResult) {
        super.onPostExecute(searchResult);
        callback.onSearchComplete(searchResult);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onError();
    }

    public SearchFilesTask setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }

    public interface Callback {
        void onSearchComplete(DbxFiles.SearchResult searchResult);

        void onError();
    }
}
