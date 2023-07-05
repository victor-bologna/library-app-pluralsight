package com.pluralsight.repository;

import com.pluralsight.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookDao extends AbstractDao implements Dao<Book> {
    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> book = Optional.empty();
        String sql = "SELECT ID, TITLE FROM BOOK WHERE ID = ?";
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                Book resultSetBook = new Book();
                if (resultSet.next()) {
                    resultSetBook.setId(resultSet.getLong("id"));
                    resultSetBook.setTitle(resultSet.getString("title"));
                }
                book = Optional.of(resultSetBook);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return book;
    }
/*
    @Override
    public List<Book> findAll() {
        List<Book> bookList = Collections.emptyList();
        String sql = "SELECT * FROM BOOK";
        try (Connection connection = getConnection(); //AbstractDao
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            bookList = new ArrayList<>();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setTitle(resultSet.getString("title"));
                bookList.add(book);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return bookList;
    }
    */
    @Override
    public List<Book> findAll() {
        List<Book> bookList = Collections.emptyList();
        String sql = "SELECT * FROM BOOK";
        JdbcQueryTemplate<Book> jdbcQueryTemplateBook = new JdbcQueryTemplate<>() {
            @Override
            public Book mapItem(ResultSet resultSet) throws SQLException {
                Book book = new Book();
                book.setId(resultSet.getLong("ID"));
                book.setTitle(resultSet.getString("TITLE"));
                book.setRating(resultSet.getInt("RATING"));
                return book;
            }
        };
        bookList = jdbcQueryTemplateBook.queryForList(sql);
        return bookList;
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO BOOK (TITLE) VALUES (?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    book.setId(resultSet.getLong(1));
                }
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE BOOK SET TITLE = ? WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setLong(2, book.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return book;
    }

    @Override
    public int[] update(List<Book> bookList) {
        int[] records = {};
        String sql = "UPDATE BOOK SET TITLE = ?, RATING = ? WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Book book : bookList) {
                preparedStatement.setString(1, book.getTitle());
                preparedStatement.setInt(2, book.getRating());
                preparedStatement.setLong(3, book.getId());
                preparedStatement.addBatch();
            }
            records = preparedStatement.executeBatch();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return records;
    }

    @Override
    public int delete(Book book) {
        int rowsAffected = 0;
        String sql = "DELETE FROM BOOK WHERE ID = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, book.getId());
            rowsAffected = preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return rowsAffected;
    }
}
