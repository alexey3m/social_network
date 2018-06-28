package com.getjavajob.training.web1803.dao;

import java.sql.Connection;

public interface Pool {

    Connection getConnection();

    void returnConnection();

    void commit();

    void rollback();
}
