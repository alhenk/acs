package kz.trei.acs.action;

import kz.trei.acs.db.DbManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by alhen on 4/17/14.
 */
public class DBInit implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(DBInit.class);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        boolean tableExist=DbManager.isTableExist("Users");
        LOGGER.debug("Table exist = " + tableExist);
        if(!tableExist){
        DbManager.createUserTable();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
