package kz.trei.acs.listener;

import kz.trei.acs.dao.DaoException;
import kz.trei.acs.dao.DaoFactory;
import kz.trei.acs.dao.UserDao;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

public class ContextListener implements ServletContextListener {
    static {
        PropertyManager.load("configure.properties");
    }

    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DaoFactory daoFactory = DaoFactory.getFactory();
        UserDao userDao = daoFactory.getUserDao();
        String users = PropertyManager.getValue("user.table");
        boolean userTableExist = DbUtil.isTableExist(users);
        LOGGER.debug("Table exist = " + userTableExist);
        if (!userTableExist) {
            try {
                userDao.createTable();
            } catch (DaoException e) {
                LOGGER.error("Create user table exception " + e.getMessage());
            }
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = null;
        LOGGER.info("Context destroyed");
        try {
            connectionPool = ConnectionPool.getInstance();
        } catch (ConnectionPoolException e) {
            LOGGER.error("Get connection pool instance exception " + e.getMessage());
        }
        connectionPool.closeConnections();
    }
}
