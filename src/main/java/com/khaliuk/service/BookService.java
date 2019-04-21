package com.khaliuk.service;

import com.khaliuk.model.Author;
import com.khaliuk.model.Book;
import java.util.List;
import java.util.Optional;

public interface BookService {

    List<Book> getAll();

    Optional<Book> getById(Long id);

    Optional<Book> create(Book book);

    Optional<Book> update(Book book);

    Optional<Book> deleteById(Long id);

    List<Author> getAuthorsByBookId(Long id);


    Optional<Author> getAuthorByIdFromBook(Long bookId, Long authorId);

    Optional<Book> addAuthorToBook(Long bookId, Long authorId);

    Optional<Book> deleteAuthorFromBook(Long bookId, Long authorId);
}
