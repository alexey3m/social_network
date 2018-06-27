package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.enums.PhoneType;
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

    private ConnectionPool pool;
    private static PhoneDAO phoneDAO;

    private PhoneDAO() {
        pool = ConnectionPool.getPool();
    }

    public static PhoneDAO getInstance() {
        if (phoneDAO == null) {
            phoneDAO = new PhoneDAO();
        }
        return phoneDAO;
    }

    public boolean create(int accountId, String number, PhoneType type) throws DaoException {
        Connection connection = pool.getConnection();
        System.out.println("PhoneDao.create: " + connection);
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PHONE)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.setString(2, number);
            preparedStatement.setInt(3, type.getStatus());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    // Map<PhoneNumber, PhoneType>
    public Map<String, PhoneType> getAll(int accountId) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PHONES_BY_ACCOUNT_ID)) {
            preparedStatement.setInt(1, accountId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<String, PhoneType> phones = new HashMap<>();
            while (resultSet.next()) {
                phones.put(resultSet.getString("phone_number"), PhoneType.values()[resultSet.getInt("phone_type")]);
            }
            return phones;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    // Map<PhoneNumber, PhoneType>
    public boolean update(int accountId, Map<String, PhoneType> phones) throws DaoException {
        Connection connection = pool.getConnection();
        System.out.println("PhoneDao.update: " + connection);
        remove(accountId);
        for (Entry<String, PhoneType> phone : phones.entrySet()) {
            create(accountId, phone.getKey(), phone.getValue());
        }
        return true;
    }

    public boolean remove(int accountId) throws DaoException {
        Connection connection = pool.getConnection();
        System.out.println("PhoneDao.remove: " + connection);
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PHONES)) {
            preparedStatement.setInt(1, accountId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}