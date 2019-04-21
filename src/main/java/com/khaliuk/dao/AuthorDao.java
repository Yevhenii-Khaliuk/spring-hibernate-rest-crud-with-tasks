package com.khaliuk.dao;

import com.khaliuk.model.Author;
import com.khaliuk.util.SearchCriteria;
import java.util.List;

public interface AuthorDao {

    List<Author> getAll();

    List<Author> getAll(List<SearchCriteria> params);

    Author getById(Long id);

    Author create(Author author);

    Author update(Author author);

    Author deleteById(Long id);
}
