package kz.trei.acs.dao;

import kz.trei.acs.user.User;

/**
 * Created by alhen on 4/25/14.
 */
public interface UserDao {
    public User find(String username, String password) throws Exception;
    public void create(User user);
    public void createUserTable();
}
