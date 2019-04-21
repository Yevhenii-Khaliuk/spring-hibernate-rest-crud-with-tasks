package com.khaliuk.dao;

import com.khaliuk.model.Book;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Book> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery("from Book b", Book.class)
                .list();
    }

    @Override
    public Book getById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Book b inner join fetch b.authors where b.id = :id",
                        Book.class)
                .setParameter("id", id)
                .uniqueResult();
    }

    @Override
    public Book create(Book book) {
        sessionFactory.getCurrentSession().save(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        sessionFactory.getCurrentSession().update(book);
        return book;
    }

    @Override
    public Book deleteById(Long id) {
        Book book = getById(id);
        sessionFactory.getCurrentSession().delete(book);
        return book;
    }
}
