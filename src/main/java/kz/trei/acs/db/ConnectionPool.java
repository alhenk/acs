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
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final Queue<Connection> resources = new LinkedList<Connection>();
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static String dbDriver = PropertyManager.getValue("db.h2.driver");
    private static String dbUrl = PropertyManager.getValue("db.h2.url");
    private static String dbUser = PropertyManager.getValue("db.h2.user");
    private static String dbPassword = PropertyManager.getValue("db.h2.password");

    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        try {
            Class.forName(dbDriver);
            for(int i=0;i<POOL_SIZE;i++){
                //resources.add(DriverManager.getConnection(dbUrl,dbUser,dbPassword));
                resources.add(DriverManager.getConnection(dbUrl));
            }
        } catch (SQLException e){
            LOGGER.error("get connection exception: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            LOGGER.error("DB driver exception: " + e.getMessage());
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            if (semaphore.tryAcquire(WAIT_MAX, TimeUnit.MILLISECONDS)) {
                Connection connection = resources.poll();
                return connection;
            }
        } catch (InterruptedException e) {
            LOGGER.error("get connection exception: "+e.getMessage());
        }
        throw new ConnectionPoolException("Path wait time out");
    }

    public void returnConnection(Connection connection) {
        resources.add(connection);
        semaphore.release();
    }

    public void closeConnections(){
        for(Connection connection : resources){
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("connection close exception: " + e.getMessage());
            }
        }
    }

}
