package kz.trei.acs.dao;

import kz.trei.acs.user.User;

import java.util.List;


public interface UserDao {
    public User find(String username, String password) throws DaoException;
    public User find(long id) throws DaoException;
    public void create(User user) throws DaoException;
    public void createTable() throws DaoException;
    public List<User> list()throws DaoException;
    public void delete(long id) throws DaoException;
}
