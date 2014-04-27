package kz.trei.acs.dao;

import kz.trei.acs.user.User;

import java.util.List;

/**
 * Created by alhen on 4/25/14.
 */
public interface UserDao {
    public User find(String username, String password) throws DaoException;
    public User find(long id) throws DaoException;
    public void create(User user) throws DaoException;
    public void createUserTable() throws DaoException;
    public List<User> list()throws DaoException;
}
