package com.khaliuk.controller;

import com.khaliuk.model.Author;
import com.khaliuk.model.Book;
import com.khaliuk.service.AuthorService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        List<Author> authors = authorService.getAll();
        ResponseEntity<List<Author>> responseEntity;
        if (authors.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(authors);
        }
        return responseEntity;
    }

    @GetMapping(value = "/min-age/{years}/sorted-by-born")
    public ResponseEntity<List<Author>> getAll(@PathVariable Long years) {
        List<Author> authors = authorService.getAllWithAgeSortedByBorn(years);
        ResponseEntity<List<Author>> responseEntity;
        if (authors.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(authors);
        }
        return responseEntity;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Author> getById(@PathVariable Long id) {
        return authorService.getById(id)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PostMapping
    public ResponseEntity<Author> create(@Valid @RequestBody Author author) {
        return authorService.create(author)
                .map(a -> ResponseEntity.created(getUri(a.getId())).body(a))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    private URI getUri(Long id) {
        return URI.create(String.format("/authors/%s", id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Author> update(@RequestBody Author author, @PathVariable Long id) {
        author.setId(id);
        return authorService.update(author)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Author> delete(@PathVariable Long id) {
        return authorService.deleteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/{id}/books")
    public ResponseEntity<List<Book>> getBooksByAuthorId(@PathVariable Long id) {
        List<Book> books = authorService.getBooksByAuthorId(id);
        ResponseEntity<List<Book>> responseEntity;
        if (books.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else {
            responseEntity = ResponseEntity.ok(books);
        }
        return responseEntity;
    }

    @GetMapping(value = "/{authorId}/books/{bookId}")
    public ResponseEntity<Book> getBookByIdFromAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId) {

        return authorService.getBookByIdFromAuthor(authorId, bookId)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @PutMapping(value = "/{authorId}/books/{bookId}")
    public ResponseEntity<Author> addBookToAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId) {

        return authorService.addBookToAuthor(authorId, bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{authorId}/books/{bookId}")
    public ResponseEntity<Author> deleteBookFromAuthor(
            @PathVariable Long authorId,
            @PathVariable Long bookId) {

        return authorService.deleteBookFromAuthor(authorId, bookId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/max-books")
    public ResponseEntity<Author> getAuthorWithMaxBooks() {
        return authorService.getAuthorWithMaxBooks()
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
