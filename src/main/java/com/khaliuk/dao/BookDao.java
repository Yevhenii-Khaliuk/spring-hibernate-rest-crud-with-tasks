package com.khaliuk.dao;

import com.khaliuk.model.Book;
import java.util.List;

public interface BookDao {

    List<Book> getAll();

    Book getById(Long id);

    Book create(Book book);

    Book update(Book book);

    Book deleteById(Long id);

    List<Book> getBooksWithAuthorsWithMoreThanOneBook();
}
