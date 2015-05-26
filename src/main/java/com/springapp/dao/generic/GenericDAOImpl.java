package com.springapp.dao.generic;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class GenericDAOImpl<T, PK extends Serializable>
        implements GenericDAO<T, PK> {

    @Autowired
    private SessionFactory sessionFactory;

    private Class<T> type;

    public GenericDAOImpl() {
    }

    public GenericDAOImpl(Class<T> type) {
        this.type = type;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    @Transactional
    public void add(T object) {
        Session session = getSession();
        session.save(object);
    }

    @Override
    @Transactional
    public T get(PK id) {
        return (T) getSession().load(type, id);
    }

    @Override
    @Transactional
    public void update(T o) {
        Session session = getSession();
        session.update(o);
        session.flush();
    }

    @Override
    @Transactional
    public void delete(PK id) {
        Session session = getSession();
        session.delete(session.load(type,
                id));
    }

    @Override
    @Transactional
    public <T> List<T> getAll() {
        String sql = "from " + type.getSimpleName();
        return getSession().createQuery(sql).setCacheable(true).list();
    }
}
