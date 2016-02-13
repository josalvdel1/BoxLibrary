package com.josalvdel1.boxlibrary.epub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.FileInputStream;
import java.io.IOException;

import javax.inject.Inject;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.epub.EpubReader;

public class SimpleEpubReader {

    public static final String TAG = "SimpleEpubReader";
    private EpubReader reader;

    @Inject
    public SimpleEpubReader(EpubReader epubReader) {
        reader = epubReader;
    }

    public Book readEpub(String epubPath) {
        Book book = null;
        try {
            book = reader.readEpub(new FileInputStream(epubPath));
        } catch (IOException e) {
            Log.e(TAG, "Operation READ failed " + e.getMessage());
        }
        return book;
    }

    public Bitmap getCoverBitmap(Book book) {
        Bitmap cover = null;
        try {
            cover = BitmapFactory.decodeStream(book.getCoverImage().getInputStream());
        } catch (Exception e) {
            Log.e("Failed to get Cover", e.getMessage());
        }
        return cover;
    }

    public com.josalvdel1.boxlibrary.entities.Book getBook(String bookPath) {
        com.josalvdel1.boxlibrary.entities.Book book = null;

        Book epub = readEpub(bookPath);
        if (epub.getGuide().getReferences().size() != 0) {
            book = new com.josalvdel1.boxlibrary.entities.Book(epub.getTitle(), getCoverBitmap(epub), null, null, bookPath);

            Metadata metadata = epub.getMetadata();
            if (metadata != null) {
                book.setAuthor(metadata.getAuthors() != null ? metadata.getAuthors().get(0).toString() : null);
                book.setDescription(metadata.getDescriptions() != null ? metadata.getDescriptions().get(0) : null);
            }

        }
        return book;
    }
}
