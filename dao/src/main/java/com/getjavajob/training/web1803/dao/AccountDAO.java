package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SEARCH_ACCOUNTS_BY_STRING = "SELECT * FROM account WHERE LOWER(first_name) LIKE ? " +
            "UNION SELECT * FROM account WHERE LOWER(last_name) LIKE ? " +
            "UNION SELECT * FROM account WHERE LOWER(middle_name) LIKE ? ORDER BY account_id";
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

    private Pool pool;
    private static AccountDAO accountDAO;

    private AccountDAO() {
        pool = ConnectionPool.getPool();
    }

    //Constructor for tests
    public AccountDAO(Pool pool) {
        this.pool = pool;
    }

    public static AccountDAO getInstance() {
        if (accountDAO == null) {
            accountDAO = new AccountDAO();
        }
        return accountDAO;
    }

    public boolean create(String email, String password, String firstName, String lastName, String middleName,
                          String birthday, InputStream photo, String photoFileName, String skype, int icq, String regDate,
                          Role role) throws DaoException, DaoNameException {
        Connection connection = pool.getConnection();
        System.out.println("AccountDao.create: " + connection);
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_EMAIL)) {
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
        }
    }

    public Account get(int id) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return createAccountFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public int getId(String email) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ACCOUNT_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(COLUMN_ACCOUNT_ID);
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return 0;
    }

    public int loginAndGetId(String email, String password) throws DaoException, DaoNameException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(CHECK_EMAIL_AND_PASSWORD)) {
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
        }
    }

    public Role getRole(int accountId) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ROLE)) {
            preparedStatement.setInt(1, accountId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Role.values()[resultSet.getInt("role")];
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return null;
    }

    public List<Account> searchByString(String search) throws DaoException {
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_ACCOUNTS_BY_STRING)) {
            String lowerSearch = search.toLowerCase();
            preparedStatement.setString(1, "%" + lowerSearch + "%");
            preparedStatement.setString(2, "%" + lowerSearch + "%");
            preparedStatement.setString(3, "%" + lowerSearch + "%");
            List<Account> accounts = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    accounts.add(createAccountFromResult(resultSet));
                }
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Account account) throws DaoException {
        Connection connection = pool.getConnection();
        System.out.println("AccountDao.update: " + connection);
        int id = account.getId();
        executePrepStatementUpdateString(id, account.getPassword(), connection, UPDATE_ACCOUNT_SET_PASSWORD);
        executePrepStatementUpdateString(id, account.getFirstName(), connection, UPDATE_ACCOUNT_SET_FIRST_NAME);
        executePrepStatementUpdateString(id, account.getLastName(), connection, UPDATE_ACCOUNT_SET_LAST_NAME);
        executePrepStatementUpdateString(id, account.getMiddleName(), connection, UPDATE_ACCOUNT_SET_MIDDLE_NAME);
        executePrepStatementUpdateString(id, account.getBirthday(), connection, UPDATE_ACCOUNT_SET_BIRTHDAY);
        byte[] photo = account.getPhoto();
        if (photo != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ACCOUNT_SET_PHOTO)) {
                preparedStatement.setBytes(1, photo);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        executePrepStatementUpdateString(id, account.getPhotoFileName(), connection, UPDATE_ACCOUNT_SET_PHOTO_FILE_NAME);
        executePrepStatementUpdateString(id, account.getSkype(), connection, UPDATE_ACCOUNT_SET_SKYPE);
        executePrepStatementUpdateInt(id, account.getIcq(), connection, UPDATE_ACCOUNT_SET_ICQ);
        return true;
    }

    public boolean updateRole(int accountId, Role newRole) throws DaoException {
        Connection connection = pool.getConnection();
        System.out.println("AccountDao.updateRole: " + connection);
        executePrepStatementUpdateInt(accountId, newRole.getStatus(), connection, UPDATE_ACCOUNT_SET_ROLE);
        return true;
    }

    public boolean remove(int id) throws DaoException {
        Connection connection = pool.getConnection();
        System.out.println("AccountDao.remove: " + connection);
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(REMOVE_ACCOUNT)) {
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static void executePrepStatementUpdateInt(int id, int field, Connection connection, String query) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, field);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
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
        Connection connection = pool.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_NEW_ACCOUNT)) {
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
        return account;
    }
}