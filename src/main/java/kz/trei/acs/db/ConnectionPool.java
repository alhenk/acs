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
            .getValue("db.connection.poolSize"));
    private final static long WAIT_MAX = Long.valueOf(PropertyManager.getValue("db.connection.wait.millis.max"));
    private final Semaphore semaphore = new Semaphore(POOL_SIZE, true);
    private final Queue<Connection> resources = new LinkedList<Connection>();
    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;


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
            Class.forName("org.h2.Driver");
            for(int i=0;i<POOL_SIZE;i++){
                resources.add(DriverManager.getConnection("jdbc:h2:~/acs"));
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            if (semaphore.tryAcquire(WAIT_MAX, TimeUnit.MILLISECONDS)) {
                Connection connection = resources.poll();
                return connection;
            }
        } catch (InterruptedException e) {
            LOGGER.error(e);
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
                e.printStackTrace();
            }
        }
    }

}
