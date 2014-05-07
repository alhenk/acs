package kz.trei.acs.dao;

import kz.trei.acs.user.User;

import java.util.List;

/**
 * Created by Admin on 07.05.14.
 */
public interface AbstractDao<T> {
    public User findById(long id) throws DaoException;
    public void create(T entity) throws DaoException;
    public long totalNumber() throws DaoException;
    public void update(T entity) throws DaoException;
    public List<T> findAll()throws DaoException;
    public void delete(long id) throws DaoException;
}
