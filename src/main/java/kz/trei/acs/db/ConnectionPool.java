package kz.trei.acs.db;

import kz.trei.acs.util.PropertyManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConnectionPool {
    static {
        PropertyManager.load("configure.properties");
    }

    private final static int POOL_SIZE = Integer.valueOf(PropertyManager
            .getValue("connection.poolSize"));
    private final static long WAIT_MAX = Long.valueOf(PropertyManager.getValue("connection.wait.millis.max"));
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static volatile ConnectionPool instance;
    private static String dbName = PropertyManager.getValue("db.name");
    private static String dbDriver = PropertyManager.getValue("db." + dbName + ".driver");
    private static String dbUrl = PropertyManager.getValue("db." + dbName + ".url");
    private static String dbUser = PropertyManager.getValue("db." + dbName + ".user");
    private static String dbPassword = PropertyManager.getValue("db." + dbName + ".password");
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final Queue<Connection> resources = new LinkedList<Connection>();

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        ConnectionPool localInstance = instance;
        if (localInstance == null) {
            synchronized (ConnectionPool.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new ConnectionPool();
                    instance.init();
                }
            }
        }
        return localInstance;
    }

    private void init() throws ConnectionPoolException {
        try {
            Class.forName(dbDriver);
            for (int i = 0; i < POOL_SIZE; i++) {
                //resources.add(DriverManager.getConnection(dbUrl,dbUser,dbPassword));
                resources.add(DriverManager.getConnection(dbUrl));
            }
        } catch (SQLException e) {
            LOGGER.error("Get DB connection exception: " + e.getMessage());
            throw new ConnectionPoolException("Get DB connection exception");
        } catch (ClassNotFoundException e) {
            LOGGER.error("DB driver class loader exception: " + e.getMessage());
            throw new ConnectionPoolException("DB driver class loader exception");
        }
    }

    public String getDbName() {
        return dbName;
    }

    public Connection getConnection() throws ConnectionPoolException {
        LOGGER.debug("getConnection ...");
        try {
            if (semaphore.tryAcquire(WAIT_MAX, TimeUnit.MILLISECONDS)) {
                Connection connection = resources.poll();
                LOGGER.debug("get connection total = " + resources.size());
                return connection;
            }
        } catch (InterruptedException e) {
            LOGGER.error("Get DB connection exception: " + e.getMessage());
            throw new ConnectionPoolException("Get DB connection exception");
        }
        LOGGER.debug("... time out");
        throw new ConnectionPoolException("Path wait time out");
    }

    public void returnConnection(Connection connection) {
        resources.add(connection);
        semaphore.release();
        LOGGER.debug("return connection total = " + resources.size());
    }

    public void closeConnections() {
        for (Connection connection : resources) {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    LOGGER.error("Close all connections exception: " + e.getMessage());
                }
            }
        }
    }

}
