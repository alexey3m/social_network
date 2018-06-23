package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.dao.exceptions.DaoException;

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
    private Queue<Connection> freeConnections;
    private Properties properties;
    private Semaphore semaphore;

    private ConnectionPool() throws DaoException {
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
        }
    }

    static ConnectionPool getPool() throws DaoException {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    public Connection getConnection() throws DaoException {
        try {
            semaphore.acquire();
            Connection connection = freeConnections.poll();
            boolean isValid = false;
            if (connection != null) {
                isValid = connection.isValid(0);
                if (!isValid) {
                    connection.close();
                }
            }
            return isValid ? connection : newConnection();
        } catch (InterruptedException | SQLException e) {
            throw new DaoException(e);
        }
    }

    private Connection newConnection() throws DaoException {
        String url = properties.getProperty("database.url");
        String user = properties.getProperty("database.user");
        String password = properties.getProperty("database.password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DaoException(e);
        }
    }

    public void returnConnection(Connection connection) throws DaoException {
        try {
            boolean isValid = connection.isValid(0);
            if (!isValid) {
                connection.close();
            }
            freeConnections.add(isValid ? connection : newConnection());
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        semaphore.release();
    }
}