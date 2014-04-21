package kz.trei.acs.listener;

import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.DbManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by alhen on 4/17/14.
 */
public class DBInit implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DBInit.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean tableExist=DbManager.isTableExist("ACSUSERS");
        LOGGER.debug("Table exist = " + tableExist);
        if(!tableExist){
            DbManager.createUserTable();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closeConnections();
    }
}
