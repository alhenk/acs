package kz.trei.acs.dao;

import kz.trei.acs.user.User;

import java.util.List;

/**
 * Created by Admin on 07.05.14.
 */
public interface Dao<T> {
    public T findById(long id) throws DaoException;
    public void create(T entity) throws DaoException;
    public long numberOfTuples() throws DaoException;
    public void update(T entity) throws DaoException;
    public List<T> findAll()throws DaoException;
    public  List<T> findInRange(long offset, long limit) throws DaoException;
    public void delete(long id) throws DaoException;
}
