package com.getjavajob.training.web1803.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class ConnectionPool implements Pool {
    private static ConnectionPool pool;
    private static Queue<Connection> freeConnections;
    private static Properties properties;
    private static Semaphore semaphore;
    private static ThreadLocal<Connection> threadLocal;

    private ConnectionPool() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream("DBconnectLocal.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            int size = Integer.valueOf(properties.getProperty("pool.size"));
            freeConnections = new ConcurrentLinkedQueue<>();
            semaphore = new Semaphore(size);
            for (int i = 0; i < size; i++) {
                freeConnections.add(newConnection());
            }
            threadLocal = ThreadLocal.withInitial(ConnectionPool::getConnectionToThread);
        }
    }

    public static ConnectionPool getPool() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    private static Connection getConnectionToThread() {
        Connection connection = null;
        try {
            semaphore.acquire();
            connection = freeConnections.poll();
            if (connection != null) {
                if (!connection.isValid(0)) {
                    connection.close();
                    connection = newConnection();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public Connection getConnection() {
        Connection connection = threadLocal.get();
        try {
            if (!connection.isValid(0)) {
                connection.close();
                threadLocal.remove();
                freeConnections.add(newConnection());
                return threadLocal.get();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private static Connection newConnection() {
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String password = properties.getProperty("database.password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void returnConnection() {
        Connection connection = threadLocal.get();
        threadLocal.remove();
        try {
            if (!connection.isValid(0)) {
                connection.close();
                freeConnections.add(newConnection());
            } else {
                freeConnections.add(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        semaphore.release();
    }

    public void commit() {
        for (Connection conn : freeConnections) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Connection connection = threadLocal.get();
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rollback() {
        Connection connection = threadLocal.get();
        try {
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}