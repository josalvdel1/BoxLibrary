package com.josalvdel1.boxlibrary.entities;

import java.io.Serializable;

public class Book implements Serializable {

    private String title;
    private String cover;

    public Book(String title, String cover) {
        this.title = title;
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return title.equals(book.title);

    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
