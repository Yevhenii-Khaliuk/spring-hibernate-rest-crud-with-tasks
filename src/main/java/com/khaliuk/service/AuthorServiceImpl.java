package com.khaliuk.service;

import com.khaliuk.dao.AuthorDao;
import com.khaliuk.dao.BookDao;
import com.khaliuk.model.Author;
import com.khaliuk.model.Book;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private BookDao bookDao;

    @Override
    public List<Author> getAll() {
        return authorDao.getAll();
    }

    @Override
    public Optional<Author> getById(Long id) {
        return Optional.ofNullable(authorDao.getById(id));
    }

    @Override
    public Optional<Author> create(Author author) {
        return Optional.ofNullable(authorDao.create(author));
    }

    @Override
    public Optional<Author> update(Author author) {
        return Optional.ofNullable(authorDao.update(author));
    }

    @Override
    public Optional<Author> deleteById(Long id) {
        return Optional.ofNullable(authorDao.deleteById(id));
    }

    @Override
    public List<Book> getBooksByAuthorId(Long id) {
        Author author = authorDao.getById(id);
        return author != null ? author.getBooks() : Collections.emptyList();
    }

    @Override
    public Optional<Author> deleteBookFromAuthor(Long authorId, Long bookId) {
        Author author = authorDao.getById(authorId);
        if (author != null) {
            if (authorHasBookWithId(author, bookId)) {
                List<Book> resultBooks = author.getBooks().stream()
                        .filter(book -> !book.getId().equals(bookId))
                        .collect(Collectors.toList());
                author.setBooks(resultBooks);
                authorDao.update(author);
            } else {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Optional<Author> addBookToAuthor(Long authorId, Long bookId) {
        Author author = authorDao.getById(authorId);
        Book book = bookDao.getById(bookId);
        if (author != null) {
            if (book != null) {
                author.getBooks().add(book);
                authorDao.update(author);
            } else {
                return Optional.empty();
            }
        }
        return Optional.ofNullable(author);
    }

    @Override
    public Optional<Book> getBookByIdFromAuthor(Long authorId, Long bookId) {
        Author author = authorDao.getById(authorId);
        if (author != null && authorHasBookWithId(author, bookId)) {
            return Optional.ofNullable(bookDao.getById(bookId));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAllWithAgeSortedByBorn(Long years) {
        return authorDao.getAllWithAgeSortedByBorn(years);
    }

    private boolean authorHasBookWithId(Author author, Long bookId) {
        return author.getBooks().stream()
                .anyMatch(book -> book.getId().equals(bookId));
    }
}
