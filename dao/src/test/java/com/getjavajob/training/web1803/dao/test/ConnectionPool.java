package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.dao.Pool;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPool implements Pool {
    private String url;
    private String user;
    private String password;

    public ConnectionPool() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("H2connect.properties"));
            url = properties.getProperty("database.url");
            user = properties.getProperty("database.user");
            password = properties.getProperty("database.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void returnConnection() {
    }

    @Override
    public void commit() {
    }

    @Override
    public void rollback() {
    }
}
