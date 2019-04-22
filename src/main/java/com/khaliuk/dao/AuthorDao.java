package com.khaliuk.dao;

import com.khaliuk.model.Author;
import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    Author getById(Long id);

    Author create(Author author);

    Author update(Author author);

    Author deleteById(Long id);

    List<Author> getAllWithAgeSortedByBorn(Long years);
}
