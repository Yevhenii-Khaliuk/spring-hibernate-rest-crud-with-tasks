package com.khaliuk.dao;

import com.khaliuk.model.Author;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Author> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Author a", Author.class)
                .list();
    }

    @Override
    public Author getById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Author a inner join fetch a.books where a.id = :id",
                        Author.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public Author create(Author author) {
        sessionFactory.getCurrentSession().save(author);
        return author;
    }

    @Override
    public Author update(Author author) {
        sessionFactory.getCurrentSession().update(author);
        return author;
    }

    @Override
    public Author deleteById(Long id) {
        Author author = getById(id);
        sessionFactory.getCurrentSession().delete(author);
        return author;
    }
}
