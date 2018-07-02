package com.getjavajob.training.web1803.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JNDIPool implements Pool {
    private static final JNDIPool INSTANCE = new JNDIPool();
    private DataSource dataSource;

    private JNDIPool() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/socnet");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public static JNDIPool getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void returnConnection() {
    }

    public void commit() {
    }

    public void rollback() {
    }
}