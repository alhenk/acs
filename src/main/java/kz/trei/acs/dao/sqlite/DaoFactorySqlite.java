package kz.trei.acs.dao.sqlite;

import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;

/**
 * Created by alhen on 4/25/14.
 */
public class DaoFactorySqlite extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoSqlite();
    }
}
