package kz.trei.acs.listener;

import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.DbManager;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInit implements ServletContextListener {
    static {
        PropertyManager.load("configure.properties");
    }
    private static final Logger LOGGER = Logger.getLogger(DBInit.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        String users = PropertyManager.getValue("db.user.table");
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
    }
}
