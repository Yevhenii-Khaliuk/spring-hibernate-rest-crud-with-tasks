package com.khaliuk.dao;

import com.khaliuk.model.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import org.hibernate.Session;
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

    @Override
    public List<Book> getBooksWithAuthorsWithMoreThanOneBook() {
        return sessionFactory.getCurrentSession().createQuery(
                "from Book b inner join fetch b.authors a where size(a.books) >= 2",
                Book.class).list();
    }

    @Override
    public Map<String, Long> getGenresWithCalculatedNumbers() {
        Session session = sessionFactory.getCurrentSession();
        List<String> genres = session.createQuery(
                "select b.genre from Book b group by genre", String.class)
                .list();
        List<Long> counts = session.createQuery(
                "select count(genre) from Book b group by genre", Long.class)
                .list();
        Map<String, Long> result = new HashMap<>();
        genres.forEach(genre -> result.put(genre, counts.get(genres.indexOf(genre))));

        return result;
        /*String genre  = "SELECT book.genre FROM Book book  GROUP BY genre";
    SQLQuery query= session.createSQLQuery(genre);
    List<String>  genreList = query.list();

    String count = "SELECT  COUNT(genre) AS counter FROM book book  GROUP BY genre";
    query= session.createSQLQuery(count);
    List<Integer>  countList = query.list();

   Map<String, Integer> task4List = new HashMap<String, Integer>();
    for (String genre : genreList) {
         task4List.put(genre ,countList.get(genreList.indexOf(genre)));
    }*/
    }
}
