package kz.trei.acs.dao;

/**
 * Created by alhen on 4/25/14.
 */
public class DaoFactoryH2 extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoH2();
    }
}
