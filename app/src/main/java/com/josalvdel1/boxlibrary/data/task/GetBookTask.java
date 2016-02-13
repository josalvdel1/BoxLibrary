package com.josalvdel1.boxlibrary.data.task;

import android.app.Application;
import android.os.AsyncTask;

import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.epub.SimpleEpubReader;

import java.io.File;

import javax.inject.Inject;

public class GetBookTask extends AsyncTask<String, Book, Book> {

    private Application application;
    private Callback callback;
    private SimpleEpubReader epubReader;
    private String path;

    @Inject
    public GetBookTask(BoxLibraryApplication application, SimpleEpubReader epubReader) {
        this.application = application;
        this.epubReader = epubReader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback == null) {
            throw new IllegalStateException("Callback should not be null");
        }
        if (path == null) {
            throw new IllegalStateException("Path should not be null");
        }
    }

    @Override
    protected Book doInBackground(String... params) {
        Book processedBook = null;
        File booksFiles = application.getExternalFilesDir("books");
        if (booksFiles != null && path != null) {
            processedBook = epubReader.getBook(path);
        } else {
            callback.onError();
        }
        return processedBook;
    }

    @Override
    protected void onPostExecute(Book book) {
        super.onPostExecute(book);
        callback.onBookAvailable(book);
    }

    public GetBookTask setBookPath(String path) {
        this.path = path;
        return this;
    }

    public GetBookTask setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }


    public interface Callback {
        void onBookAvailable(Book book);

        void onError();
    }
}
