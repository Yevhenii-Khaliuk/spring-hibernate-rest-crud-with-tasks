package com.khaliuk.service;

import com.khaliuk.model.Author;
import com.khaliuk.model.Book;
import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> getAll();

    Optional<Author> getById(Long id);

    Optional<Author> create(Author author);

    Optional<Author> update(Author author);

    Optional<Author> deleteById(Long id);

    List<Book> getBooksByAuthorId(Long id);

    Optional<Author> deleteBookFromAuthor(Long authorId, Long bookId);

    Optional<Author> addBookToAuthor(Long authorId, Long bookId);

    Optional<Book> getBookByIdFromAuthor(Long authorId, Long bookId);

    List<Author> getAllWithAgeSortedByBorn(Long years);

    Optional<Author> getAuthorWithMaxBooks();
}
