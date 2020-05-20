package com.sula.dao;

import com.sula.entity.SuperEntity;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Component
public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID> {


    protected Session session;
    private Class<T> entity;

    @Autowired
    HibernateTemplate hibernateTemplate;

    public CrudDAOImpl() {
        entity = (Class<T>)(((ParameterizedType)(this.getClass().getGenericSuperclass())).
                getActualTypeArguments()[0]);
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void delete(ID id) throws Exception {
        session.delete(session.load(entity, id));
    }

    @Override
    public List<T> findAll() throws Exception {
        return session.createQuery("FROM " + entity.getName()).list();
    }

    @Override
    public T find(ID id) throws Exception {
        return session.get(entity, id);
    }

    @Override
    public void save(T entity) throws Exception {
        session.save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        session.merge(entity);
    }


}
