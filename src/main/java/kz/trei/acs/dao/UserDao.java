package kz.trei.acs.dao;

import kz.trei.acs.user.User;

import java.util.List;

/**
 * Created by alhen on 4/25/14.
 */
public interface UserDao {
    public User find(String username, String password) throws Exception;
    public User find(long id) throws Exception;
    public void create(User user);
    public void createUserTable();
    public List<User> list()throws Exception;
}
