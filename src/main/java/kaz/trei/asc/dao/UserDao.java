package kaz.trei.asc.dao;

import kaz.trei.asc.user.User;

public class UserDao {

    public User find(String username,String password) throws Exception {
        if(username == null || password == null || username.isEmpty() || password.isEmpty()){
            throw new Exception();
        }
        if(username.equals("admin") && password.equals("123")){
            return new User();
        }
        throw new Exception();
    }
}
