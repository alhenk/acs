package kz.trei.acs.dao;

import kz.trei.acs.user.User;

import java.util.List;


public interface UserDao extends AbstractDao<User>{
    public User find(String username, String password) throws DaoException;
    public void createTable() throws DaoException;
    public List<User> findByName(String username) throws DaoException;
}
