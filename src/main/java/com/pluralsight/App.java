package com.pluralsight;

import com.pluralsight.model.Book;
import com.pluralsight.repository.BookDao;
import com.pluralsight.repository.Dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        Dao<Book> bookDao = new BookDao();
        List<Book> bookList = bookDao.findAll();

        for (Book book : bookList) {
            System.out.println("Get all: Id = " + book.getId());
            System.out.println("Get all: Title = " + book.getTitle());
            System.out.println("Get all: Rating = " + book.getRating());
        }
/*
        Optional<Book> optionalBook = bookDao.findById(1L);
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            System.out.println("Optional find by id: Id = " + book.getId());
            System.out.println("Optional find by id: Title = " + book.getTitle());

            book.setTitle("Effective Java: Second Edition");

            book = bookDao.update(book);

            System.out.println("Optional find by id: Id = " + book.getId());
            System.out.println("Optional find by id: Title = " + book.getTitle());
        }

        Book newBook = new Book();
        newBook.setTitle("The River Why");
        newBook = bookDao.create(newBook);

        System.out.println("Create book: Id = " + newBook.getId());
        System.out.println("Create book: Title = " + newBook.getTitle());

        int numDel = bookDao.delete(newBook);

        System.out.println("Number of rows deleted: " + numDel);

        bookList = bookDao.findAll();
        List<Book> collectedBooks = bookList.stream()
                .peek(b -> b.setRating(5))
                .collect(Collectors.toList());
        bookDao.update(collectedBooks);

        int i = bookDao.delete(bookList.get(0));*/
    }
}
