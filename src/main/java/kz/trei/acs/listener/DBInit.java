package kz.trei.acs.listener;

import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.DbManager;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.*;
import java.util.Enumeration;

public class DBInit implements ServletContextListener {
    static {
        PropertyManager.load("configure.properties");
    }
    private static final Logger LOGGER = Logger.getLogger(DBInit.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String users = PropertyManager.getValue("user.table");
        boolean userTableExist=DbManager.isTableExist(users);
        LOGGER.debug("Table exist = " + userTableExist);
        if(!userTableExist){
            DbManager.createUserTable();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closeConnections();
        // This manually deregisters JDBC driver, which prevents Tomcat 7 from complaining about memory leaks wrto this class
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                LOGGER.info(String.format("deregistering jdbc driver: %s", driver));
            } catch (SQLException e) {
                LOGGER.info(String.format("Error deregistering driver %s", driver), e);
            }

        }
    }
}
