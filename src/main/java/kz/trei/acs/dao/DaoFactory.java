package kz.trei.acs.dao;

import kz.trei.acs.dao.h2.DaoFactoryH2;
import kz.trei.acs.dao.sqlite.DaoFactorySqlite;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbType;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

public abstract class DaoFactory {
    private static final Logger LOGGER = Logger.getLogger(DaoFactory.class);
    static {
        PropertyManager.load("configure.properties");
    }
    public static ConnectionPool connectionPool;
    static {
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Connection pool get instance exception");
        }
    }

    public static DaoFactory getFactory() {
        DaoFactory daoFactory = null;
        DbType base = DbType.valueOf(connectionPool.getDbName().toUpperCase());
        switch (base) {
            case H2:
                daoFactory = new DaoFactoryH2();
                break;
            case SQLITE:
                daoFactory = new DaoFactorySqlite();
        }
        return daoFactory;
    }

    public abstract UserDao getUserDao();
    public abstract EmployeeDao getEmployeeDao();
    public abstract RfidTagDao getRfidTagDao();
    public abstract AttendanceDao getAttendanceDao();

}
