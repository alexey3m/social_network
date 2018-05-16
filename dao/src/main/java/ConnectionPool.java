import exceptions.DaoException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class ConnectionPool {
    private static ConnectionPool pool;
    private Queue<Connection> freeConnections;
    private Properties properties;
    private Semaphore semaphore;

    public ConnectionPool() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream("DBconnect.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int size = Integer.valueOf(properties.getProperty("pool.size"));
            freeConnections = new ConcurrentLinkedQueue<>();
            semaphore = new Semaphore(size);
        }
    }

    public static ConnectionPool getPool() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() throws DaoException {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new DaoException(e);
        }
        Connection connection = freeConnections.poll();
        if (connection == null) {
            String url = properties.getProperty("database.url");
            String user = properties.getProperty("database.user");
            String password = properties.getProperty("database.password");
            try {
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                return connection;
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        } else {
            return connection;
        }
    }

    public void returnConnection(Connection connection) {
        freeConnections.add(connection);
        semaphore.release();
    }
}