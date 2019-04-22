package com.khaliuk.controller;

import com.khaliuk.model.Author;
import com.khaliuk.model.Book;
import com.khaliuk.service.BookService;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.getAll();
        ResponseEntity<List<Book>> responseEntity;
        if (books.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(books);
        }
        return responseEntity;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return bookService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody Book book) {
        return bookService.create(book)
                .map(b -> ResponseEntity.created(getUri(b.getId())).body(b))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    private URI getUri(Long id) {
        return URI.create(String.format("/books/%s", id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Book> update(@RequestBody Book book, @PathVariable Long id) {
        book.setId(id);
        return bookService.update(book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Book> delete(@PathVariable Long id) {
        return bookService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/authors")
    public ResponseEntity<List<Author>> getAuthorsByBookId(@PathVariable Long id) {
        List<Author> authors = bookService.getAuthorsByBookId(id);
        ResponseEntity<List<Author>> responseEntity;
        if (authors.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(authors);
        }
        return responseEntity;
    }

    @GetMapping(value = "/{bookId}/authors/{authorId}")
    public ResponseEntity<Author> getAuthorByIdFromBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {

        return bookService.getAuthorByIdFromBook(bookId, authorId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping(value = "/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> addAuthorToBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {

        return bookService.addAuthorToBook(bookId, authorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{bookId}/authors/{authorId}")
    public ResponseEntity<Book> deleteAuthorFromBook(
            @PathVariable Long bookId,
            @PathVariable Long authorId) {

        return bookService.deleteAuthorFromBook(bookId, authorId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/with-authors-who-have-more-than-one-book")
    public ResponseEntity<List<Book>> getBooksWithAuthorsWithMoreThanOneBook() {
        List<Book> books = bookService.getBooksWithAuthorsWithMoreThanOneBook();
        ResponseEntity<List<Book>> responseEntity;
        if (books.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(books);
        }
        return responseEntity;
    }
}
