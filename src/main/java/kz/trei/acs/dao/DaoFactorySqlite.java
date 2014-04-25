package kz.trei.acs.dao;

/**
 * Created by alhen on 4/25/14.
 */
public class DaoFactorySqlite extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoSqlite();
    }
}
