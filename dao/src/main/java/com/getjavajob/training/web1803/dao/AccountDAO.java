package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SELECT_ACCOUNT_BY_ID = SELECT_ALL_ACCOUNTS + " WHERE account_id = ?";
    private static final String SELECT_ACCOUNT_BY_EMAIL = SELECT_ALL_ACCOUNTS + " WHERE email = ?";
    private static final String CHECK_EMAIL_AND_PASSWORD = SELECT_ACCOUNT_BY_EMAIL + " AND password = ?";
    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO account (email, password, first_name, last_name, middle_name, " +
            "birthday, photo, photo_file_name, skype, icq, reg_date, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT_SET_PASSWORD = "UPDATE account SET password = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_FIRST_NAME = "UPDATE account SET first_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_LAST_NAME = "UPDATE account SET last_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_MIDDLE_NAME = "UPDATE account SET middle_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_BIRTHDAY = "UPDATE account SET birthday = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_PHOTO = "UPDATE account SET photo = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_PHOTO_FILE_NAME = "UPDATE account SET photo_file_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_SKYPE = "UPDATE account SET skype = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_ICQ = "UPDATE account SET icq = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_SET_ROLE = "UPDATE account SET role = ? WHERE account_id = ?";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private static final String REMOVE_ACCOUNT = "DELETE FROM account WHERE account_id = ?";
    private static final String SELECT_ROLE = "SELECT role FROM account WHERE account_id = ?";

    private Connection connection;
    private Pool connectionPool;
    private PhoneDAO phoneDAO;

    public AccountDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
        phoneDAO = new PhoneDAO();
    }

    // Constructor for tests
    public AccountDAO(Pool connectionPool, PhoneDAO phoneDAO) {
        this.connectionPool = connectionPool;
        this.phoneDAO = phoneDAO;
    }

    public boolean create(String email, String password, String firstName, String lastName, String middleName,
                          String birthday, InputStream photo, String photoFileName, String skype, int icq, String regDate,
                          Role role) throws DaoException, DaoNameException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ACCOUNT_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    return insertRowInAccount(email, password, firstName, lastName, middleName, birthday, photo,
                            photoFileName, skype, icq, regDate, role);
                } else {
                    throw new DaoNameException("Email \"" + email + "\" is already used.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Account get(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public int getId(String email) throws DaoException, DaoNameException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ACCOUNT_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(COLUMN_ACCOUNT_ID);
                } else {
                    throw new DaoNameException("Email \"" + email + "\" not found in database.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public int loginAndGetId(String email, String password) throws DaoException, DaoNameException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(CHECK_EMAIL_AND_PASSWORD)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(COLUMN_ACCOUNT_ID);
                } else {
                    throw new DaoNameException("Email: \"" + email + "\" and password: " + password + " not found in database.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Role getRole(int accountId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ROLE)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Role.values()[resultSet.getInt("role")];
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    public List<Account> getAll() throws DaoException {
        connection = connectionPool.getConnection();
        try (ResultSet resultSet = this.connection.createStatement().executeQuery(SELECT_ALL_ACCOUNTS)) {
            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(createAccountFromResult(resultSet));
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean update(Account account) throws DaoException {
        connection = connectionPool.getConnection();
        int id = account.getId();
        executePrepStatementUpdateString(id, account.getPassword(), this.connection, UPDATE_ACCOUNT_SET_PASSWORD);
        executePrepStatementUpdateString(id, account.getFirstName(), this.connection, UPDATE_ACCOUNT_SET_FIRST_NAME);
        executePrepStatementUpdateString(id, account.getLastName(), this.connection, UPDATE_ACCOUNT_SET_LAST_NAME);
        executePrepStatementUpdateString(id, account.getMiddleName(), this.connection, UPDATE_ACCOUNT_SET_MIDDLE_NAME);
        executePrepStatementUpdateString(id, account.getBirthday(), this.connection, UPDATE_ACCOUNT_SET_BIRTHDAY);
        byte[] photo = account.getPhoto();
        if (photo != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_SET_PHOTO)) {
                preparedStatement.setBytes(1, photo);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        executePrepStatementUpdateString(id, account.getPhotoFileName(), this.connection, UPDATE_ACCOUNT_SET_PHOTO_FILE_NAME);
        executePrepStatementUpdateString(id, account.getSkype(), this.connection, UPDATE_ACCOUNT_SET_SKYPE);
        executePrepStatementUpdateInt(id, account.getIcq(), this.connection, UPDATE_ACCOUNT_SET_ICQ);
        try {
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean updateRole(int accountId, Role newRole) throws DaoException {
        connection = connectionPool.getConnection();
        executePrepStatementUpdateInt(accountId, newRole.getStatus(), this.connection, UPDATE_ACCOUNT_SET_ROLE);
        connectionPool.returnConnection(connection);
        return true;
    }

    public boolean remove(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(REMOVE_ACCOUNT)) {
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private static void executePrepStatementUpdateInt(int id, int field, Connection connection, String query) throws DaoException {
        if (field != 0) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, field);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }

    private static void executePrepStatementUpdateString(int id, String field, Connection connection, String query) throws DaoException {
        if (field != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, field);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }


    private boolean insertRowInAccount(String email, String password, String firstName, String lastName, String middleName,
                                       String birthday, InputStream photo, String photoFileName, String skype, int icq,
                                       String regDate, Role role) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_NEW_ACCOUNT)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, middleName);
            preparedStatement.setString(6, birthday);
            preparedStatement.setBlob(7, photo);
            preparedStatement.setString(8, photoFileName);
            preparedStatement.setString(9, skype);
            preparedStatement.setInt(10, icq);
            preparedStatement.setString(11, regDate);
            preparedStatement.setInt(12, role.getStatus());
            preparedStatement.executeUpdate();
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Account createAccountFromResult(ResultSet resultSet) throws SQLException, DaoException {
        Account account = new Account();
        int accountId = resultSet.getInt(COLUMN_ACCOUNT_ID);
        account.setId(accountId);
        account.setEmail(resultSet.getString("email"));
        account.setPassword(resultSet.getString("password"));
        account.setFirstName(resultSet.getString("first_name"));
        account.setLastName(resultSet.getString("last_name"));
        account.setMiddleName(resultSet.getString("middle_name"));
        account.setBirthday(resultSet.getString("birthday"));
        account.setPhoto(resultSet.getBytes("photo"));
        account.setPhotoFileName(resultSet.getString("photo_file_name"));
        account.setSkype(resultSet.getString("skype"));
        account.setIcq(resultSet.getInt("icq"));
        account.setRegDate(resultSet.getString("reg_date"));
        account.setRole(Role.values()[resultSet.getInt("role")]);
        account.setPhones(phoneDAO.getAll(accountId));
        return account;
    }
}