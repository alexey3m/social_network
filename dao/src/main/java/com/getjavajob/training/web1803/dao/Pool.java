package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.sql.Connection;

public interface Pool {

    Connection getConnection() throws DaoException ;

    void returnConnection(Connection connection) throws DaoException;

}
