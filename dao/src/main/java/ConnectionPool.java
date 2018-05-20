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
    private Queue<Connection> busyConnections;
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
            busyConnections = new ConcurrentLinkedQueue<>();
            semaphore = new Semaphore(size);
            System.out.println("Connection Pool created.");
        }
    }

    public static ConnectionPool getPool() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() throws DaoException {
        Connection connection = freeConnections.poll();
        if (connection == null) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new DaoException(e);
            }
            String url = properties.getProperty("database.url");
            String user = properties.getProperty("database.user");
            String password = properties.getProperty("database.password");
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);
                connection.setAutoCommit(false);
                busyConnections.add(connection);
                return connection;
            } catch (SQLException | ClassNotFoundException e) {
                throw new DaoException(e);
            }
        } else {
            return connection;
        }
    }

    public void returnConnection(Connection connection) {
        busyConnections.remove(connection);
        freeConnections.add(connection);
        semaphore.release();
    }
}