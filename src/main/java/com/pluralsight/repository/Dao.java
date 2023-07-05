package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> findById(Long id);
    List<T> findAll();
    T create (T t);
    Book update(T book);
    int[] update (List<T> bookList);

    int delete(T book);
}
