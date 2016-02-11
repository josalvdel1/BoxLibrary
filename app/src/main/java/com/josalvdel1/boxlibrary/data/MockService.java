package com.josalvdel1.boxlibrary.data;

import com.josalvdel1.boxlibrary.entities.Book;

import java.util.ArrayList;
import java.util.List;

public class MockService {

    public static List<Book> getMockBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            books.add(new Book("El guardian de las palabras", ""));
            books.add(new Book("Bridget Jones", ""));
            books.add(new Book("Las aventuras de asterix y obelix", ""));
        }
        return books;
    }
}
