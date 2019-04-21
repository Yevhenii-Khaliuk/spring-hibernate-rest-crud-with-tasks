package com.khaliuk.dao;

import com.khaliuk.model.Author;
import com.khaliuk.util.AuthorSearchQueryCriteriaConsumer;
import com.khaliuk.util.SearchCriteria;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    public List<Author> getAll(List<SearchCriteria> params) {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Author> query = builder.createQuery(Author.class);
        Root root = query.from(Author.class);
        Predicate predicate = builder.conjunction();
        AuthorSearchQueryCriteriaConsumer searchConsumer =
                new AuthorSearchQueryCriteriaConsumer(predicate, builder, root);
        params.forEach(searchConsumer);
        predicate = searchConsumer.getPredicate();
        query.where(predicate);

        return sessionFactory.getCurrentSession().createQuery(query).list();
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
