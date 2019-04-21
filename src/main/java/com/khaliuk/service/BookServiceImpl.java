package com.khaliuk.service;

import com.khaliuk.dao.AuthorDao;
import com.khaliuk.dao.BookDao;
import com.khaliuk.model.Author;
import com.khaliuk.model.Book;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private AuthorDao authorDao;

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(bookDao.getById(id));
    }

    @Override
    public Optional<Book> create(Book book) {
        return Optional.ofNullable(bookDao.create(book));
    }

    @Override
    public Optional<Book> update(Book book) {
        return Optional.ofNullable(bookDao.update(book));
    }

    @Override
    public Optional<Book> deleteById(Long id) {
        return Optional.ofNullable(bookDao.deleteById(id));
    }

    @Override
    public List<Author> getAuthorsByBookId(Long id) {
        Book book = bookDao.getById(id);
        return book != null ? book.getAuthors() : Collections.emptyList();
    }

    @Override
    public Optional<Author> getAuthorByIdFromBook(Long bookId, Long authorId) {
        Book book = bookDao.getById(bookId);
        if (book != null && bookHasAuthorWithId(book, authorId)) {
            return Optional.ofNullable(authorDao.getById(authorId));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> addAuthorToBook(Long bookId, Long authorId) {
        Book book = bookDao.getById(bookId);
        Author author = authorDao.getById(authorId);
        if (book != null) {
            if (author != null) {
                book.getAuthors().add(author);
                bookDao.update(book);
            } else {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(book);
    }

    @Override
    public Optional<Book> deleteAuthorFromBook(Long bookId, Long authorId) {
        Book book = bookDao.getById(bookId);
        if (book != null) {
            if (bookHasAuthorWithId(book, authorId)) {
                List<Author> resultAuthors = book.getAuthors().stream()
                        .filter(author -> !author.getId().equals(authorId))
                        .collect(Collectors.toList());
                book.setAuthors(resultAuthors);
                bookDao.update(book);
            } else {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(book);
    }

    private boolean bookHasAuthorWithId(Book book, Long authorId) {
        return book.getAuthors().stream()
                .anyMatch(author -> author.getId().equals(authorId));
    }
}
