package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.PhoneType;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PhoneDAO {
    private static final String SELECT_PHONES_BY_ACCOUNT_ID = "SELECT * FROM phone WHERE account_id = ?";
    private static final String INSERT_PHONE = "INSERT INTO phone (account_id, phone_number, phone_type) VALUES (?, ?, ?)";
    private static final String REMOVE_PHONES = "DELETE FROM phone WHERE account_id = ?";

    private Connection connection;
    private Pool connectionPool;

    public PhoneDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
    }

    // Constructor for tests
    public PhoneDAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean create(int accountId, String number, PhoneType type) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_PHONE)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, number);
            preparedStatement.setInt(3, type.getStatus());
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Map<PhoneNumber, PhoneType>
    public Map<String, PhoneType> getAll(int accountId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_PHONES_BY_ACCOUNT_ID)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String, PhoneType> phones = new HashMap<>();
            while (resultSet.next()) {
                phones.put(resultSet.getString("phone_number"), PhoneType.values()[resultSet.getInt("phone_type")]);
            }
            return phones;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    // Map<PhoneNumber, PhoneType>
    public boolean update(int accountId, Map<String, PhoneType> phones) throws DaoException {
        remove(accountId);
        connection = connectionPool.getConnection();
        try {
            for (Entry<String, PhoneType> phone : phones.entrySet()) {
                create(accountId, phone.getKey(), phone.getValue());
            }
            return true;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean remove(int accountId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(REMOVE_PHONES)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }
}