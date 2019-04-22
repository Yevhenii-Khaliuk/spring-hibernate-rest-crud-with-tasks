package com.khaliuk.dao;

import com.khaliuk.model.Book;
import java.util.List;
import java.util.Map;

public interface BookDao {

    List<Book> getAll();

    Book getById(Long id);

    Book create(Book book);

    Book update(Book book);

    Book deleteById(Long id);

    List<Book> getBooksWithAuthorsWithMoreThanOneBook();

    Map<String, Long> getGenresWithCalculatedNumbers();
}
