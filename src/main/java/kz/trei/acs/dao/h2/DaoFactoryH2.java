package kz.trei.acs.dao.h2;

import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;

/**
 * Created by alhen on 4/25/14.
 */
public class DaoFactoryH2 extends DaoFactory {
    @Override
    public UserDao getUserDao() {
        return new UserDaoH2();
    }
}
