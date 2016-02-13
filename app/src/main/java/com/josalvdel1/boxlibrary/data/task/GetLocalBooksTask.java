package com.josalvdel1.boxlibrary.data.task;

import android.app.Application;
import android.os.AsyncTask;

import com.josalvdel1.boxlibrary.BoxLibraryApplication;
import com.josalvdel1.boxlibrary.entities.Book;
import com.josalvdel1.boxlibrary.epub.SimpleEpubReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetLocalBooksTask extends AsyncTask<Boolean, Book, List<Book>> {

    private Application application;
    private Callback callback;
    private SimpleEpubReader epubReader;

    @Inject
    public GetLocalBooksTask(BoxLibraryApplication application, SimpleEpubReader epubReader) {
        this.application = application;
        this.epubReader = epubReader;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (callback == null) {
            throw new IllegalStateException("Callback should not be null");
        }
    }

    @Override
    protected List<Book> doInBackground(Boolean... params) {
        List<Book> books = new ArrayList<>();
        File booksFiles = application.getExternalFilesDir("books");
        if (booksFiles != null) {
            for (File bookFile : booksFiles.listFiles()) {
                Book book = epubReader.getBook(bookFile.getPath());
                //avoid invalid books
                if (book != null) {
                    //use publicProgress to invoke the callback on the UIThread
                    publishProgress(book);
                    books.add(book);
                }
            }
        } else {
            callback.onError();
        }
        return books;
    }

    @Override
    protected void onProgressUpdate(Book... values) {
        super.onProgressUpdate(values);
        callback.onBookAvailable(values[0]);
    }

    @Override
    protected void onPostExecute(List<Book> books) {
        super.onPostExecute(books);
        callback.onBooksAvailable(books);
    }

    public GetLocalBooksTask setCallback(Callback callback) {
        this.callback = callback;
        return this;
    }


    public interface Callback {
        void onBooksAvailable(List<Book> books);

        void onBookAvailable(Book book);

        void onError();
    }
}
