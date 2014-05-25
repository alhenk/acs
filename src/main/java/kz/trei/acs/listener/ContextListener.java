package kz.trei.acs.listener;

import kz.trei.acs.dao.*;
import kz.trei.acs.db.ConnectionPool;
import kz.trei.acs.db.ConnectionPoolException;
import kz.trei.acs.db.DbUtil;
import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(ContextListener.class);
    private static final String USER_TABLE = "UZERS";
    private static final String EMPLOYEE_TABLE = "EMPLOYEES";
    private static final String RFIDTAG_TABLE = "RFIDTAGS";
    private static final String ATTENDANCE_TABLE = "ATTENDANCE";

    static {
        PropertyManager.load("configure.properties");
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DaoFactory daoFactory = DaoFactory.getFactory();
        RfidTagDao rfidTagDao = daoFactory.getRfidTagDao();
        boolean isTableExist = DbUtil.isTableExist(RFIDTAG_TABLE);
        LOGGER.debug("RfidTag table exist = " + isTableExist);
        if (!isTableExist) {
            try {
                rfidTagDao.createTable();
            } catch (DaoException e) {
                LOGGER.error("Create RfidTag table exception " + e.getMessage());
            }
        }

        EmployeeDao employeeDao = daoFactory.getEmployeeDao();
        isTableExist = DbUtil.isTableExist(EMPLOYEE_TABLE);
        LOGGER.debug("Employee table exist = " + isTableExist);
        if (!isTableExist) {
            try {
                employeeDao.createTable();
            } catch (DaoException e) {
                LOGGER.error("Create Employee table exception " + e.getMessage());
            }
        }

        UserDao userDao = daoFactory.getUserDao();
        isTableExist = DbUtil.isTableExist(USER_TABLE);
        LOGGER.debug("Users table exist = " + isTableExist);
        if (!isTableExist) {
            try {
                userDao.createTable();
            } catch (DaoException e) {
                LOGGER.error("Create user table exception " + e.getMessage());
            }
        }

        AttendanceDao attendanceDao = daoFactory.getAttendanceDao();
        isTableExist = DbUtil.isTableExist(ATTENDANCE_TABLE);
        LOGGER.debug("Attendance table exist = " + isTableExist);
        if (!isTableExist) {
            try {
                attendanceDao.createTable();
            } catch (DaoException e) {
                LOGGER.error("Create Attendance table exception " + e.getMessage());
            }
        }

        boolean isViewExist = DbUtil.isTableExist("OFFICEHOURS");
        LOGGER.debug("OFFICEHOURS view exist = " + isViewExist);
        if (!isViewExist) {
            try {
                attendanceDao.createView();
            } catch (DaoException e) {
                LOGGER.error("Create Office Hours table exception " + e.getMessage());
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
