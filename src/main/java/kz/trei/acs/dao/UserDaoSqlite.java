package kz.trei.acs.dao;

import kz.trei.acs.user.User;

/**
 * Created by alhen on 4/25/14.
 */
public class UserDaoSqlite implements UserDao {
    @Override
    public User find(String username, String password) throws Exception {
        return null;
    }

    @Override
    public void create(User user) {

    }

    @Override
    public void createUserTable() {

    }
}
