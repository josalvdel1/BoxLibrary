package com.josalvdel1.boxlibrary.entities;

import android.graphics.Bitmap;

public class Book {

    private String title;
    private Bitmap cover;
    private String filePath;
    private String author;
    private String description;

    public Book(String title, Bitmap cover, String author, String description, String filePath) {
        this.cover = cover;
        this.title = title;
        this.author = author;
        this.filePath = filePath;
        this.description = description;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getCover() {
        return cover;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public void setCover(Bitmap cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book Book = (Book) o;

        return title.equals(Book.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
