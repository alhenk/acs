package kz.trei.acs.dao;

import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.DbType;
import kz.trei.acs.util.PropertyManager;

public abstract class DaoFactory {
    static{
        PropertyManager.load("configure.properties");
    }
    public static  ConnectionPool connectionPool= ConnectionPool.getInstance();

    public static DaoFactory getFactory(){
        DaoFactory daoFactory = null;
        DbType base = DbType.valueOf(connectionPool.getDbName().toUpperCase());
        switch (base){
            case H2:
                daoFactory = new DaoFactoryH2();
                break;
            case SQLITE:
                daoFactory = new DaoFactorySqlite();
        }
        return daoFactory;
    }


    public abstract UserDao getUserDao();
}
