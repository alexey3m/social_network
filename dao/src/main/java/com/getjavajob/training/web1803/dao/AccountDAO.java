import exceptions.DaoException;
import exceptions.DaoUsernameException;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class AccountDAO {
    private static final String SELECT_ALL_FROM_ACCOUNTS = "SELECT * FROM accounts WHERE username = ?";
    private static final String SELECT_ALL_ACCOUNTS_INFO = "SELECT a.account_id, username, password, " +
            "first_name, last_name, middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, " +
            "icq, skype, extra, photo, photo_file_name FROM accounts a JOIN account_info ai ON a.account_id = ai.account_id";
    private static final String SELECT_ACCOUNT_INFO = SELECT_ALL_ACCOUNTS_INFO +
            " WHERE a.account_id = ?";
    private static final String UPDATE_ACCOUNT_PASS = "UPDATE accounts SET password = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_FIRST_NAME = "UPDATE account_info SET first_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_LAST_NAME = "UPDATE account_info SET last_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_MIDDLE_NAME = "UPDATE account_info SET middle_name = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_BIRTHDAY = "UPDATE account_info SET birthday = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_PHONE_PERS = "UPDATE account_info SET phone_pers = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_PHONE_WORK = "UPDATE account_info SET phone_work = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_ADDRESS_PERS = "UPDATE account_info SET address_pers = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_ADDRESS_WORK = "UPDATE account_info SET address_work = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_EMAIL = "UPDATE account_info SET email = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_ICQ = "UPDATE account_info SET icq = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_SKYPE = "UPDATE account_info SET skype = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_EXTRA = "UPDATE account_info SET extra = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_PHOTO = "UPDATE account_info SET photo = ? WHERE account_id = ?";
    private static final String UPDATE_ACCOUNT_INFO_SET_PHOTO_FILE_NAME = "UPDATE account_info SET photo_file_name = ? WHERE account_id = ?";
    private static final String REMOVE_ACCOUNT = "DELETE FROM accounts WHERE account_id = ?";
    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO accounts (username, password) VALUES (?, ?)";
    private static final String SELECT_ACCOUNT_ID_FROM_ACCOUNTS = "SELECT account_id FROM accounts WHERE username = ?";
    private static final String INSERT_NEW_ACCOUNT_INFO = "INSERT INTO account_info (account_id, first_name, last_name, " +
            "middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, icq, skype, extra, photo, photo_file_name) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private Connection connection;
    private ConnectionPool connectionPool;

    public AccountDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();

    }

    // Constructor for tests
    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean create(String username, String password, String firstName, String lastName, String middleName,
                          String birthday, String phonePers, String phoneWork, String addressPers, String addressWork,
                          String email, int icq, String skype, String extra, InputStream photo, String photoFileName) throws DaoException, DaoUsernameException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_FROM_ACCOUNTS)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    insertRowInAccountAndAccountInfo(username, password, firstName, lastName, middleName, birthday, phonePers,
                            phoneWork, addressPers, addressWork, email, icq, skype, extra, photo, photoFileName);
                    close();
                    return true;
                } else {
                    close();
                    throw new DaoUsernameException("Username \"" + username + "\" is already used.");
                }
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Account get(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ACCOUNT_INFO)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    close();
                    return createAccountFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public int getId(String username) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_FROM_ACCOUNTS)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    close();
                    return resultSet.getInt(COLUMN_ACCOUNT_ID);
                } else {
                    close();
                    throw new DaoException("Username \"" + username + "\" not found in database.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Account> getAll() throws DaoException {
        connection = connectionPool.getConnection();
        try (ResultSet resultSet = this.connection.createStatement().executeQuery(SELECT_ALL_ACCOUNTS_INFO)) {
            close();
            List<Account> accounts = new ArrayList<>();
            while (resultSet.next()) {
                accounts.add(createAccountFromResult(resultSet));
            }

            return accounts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean updatePassword(int id, String newPassword) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_PASS)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            close();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Account account) throws DaoException {
        connection = connectionPool.getConnection();
        int id = account.getId();
        executePrepStatementUpdate(id, account.getFirstName(), this.connection, UPDATE_ACCOUNT_INFO_SET_FIRST_NAME);
        executePrepStatementUpdate(id, account.getLastName(), this.connection, UPDATE_ACCOUNT_INFO_SET_LAST_NAME);
        executePrepStatementUpdate(id, account.getMiddleName(), this.connection, UPDATE_ACCOUNT_INFO_SET_MIDDLE_NAME);
        executePrepStatementUpdate(id, account.getBirthday(), this.connection, UPDATE_ACCOUNT_INFO_SET_BIRTHDAY);
        executePrepStatementUpdate(id, account.getPhonePers(), this.connection, UPDATE_ACCOUNT_INFO_SET_PHONE_PERS);
        executePrepStatementUpdate(id, account.getPhoneWork(), this.connection, UPDATE_ACCOUNT_INFO_SET_PHONE_WORK);
        executePrepStatementUpdate(id, account.getAddressPers(), this.connection, UPDATE_ACCOUNT_INFO_SET_ADDRESS_PERS);
        executePrepStatementUpdate(id, account.getAddressWork(), this.connection, UPDATE_ACCOUNT_INFO_SET_ADDRESS_WORK);
        executePrepStatementUpdate(id, account.getEmail(), this.connection, UPDATE_ACCOUNT_INFO_SET_EMAIL);
        int icq = account.getIcq();
        if (icq != 0) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_ICQ)) {
                preparedStatement.setInt(1, icq);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        executePrepStatementUpdate(id, account.getSkype(), this.connection, UPDATE_ACCOUNT_INFO_SET_SKYPE);
        executePrepStatementUpdate(id, account.getExtra(), this.connection, UPDATE_ACCOUNT_INFO_SET_EXTRA);
        byte[] photo = account.getPhoto();
        if (photo != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_PHOTO)) {
                preparedStatement.setBytes(1, photo);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        executePrepStatementUpdate(id, account.getPhotoFileName(), this.connection, UPDATE_ACCOUNT_INFO_SET_PHOTO_FILE_NAME);
        try {
            this.connection.commit();
            close();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    static void executePrepStatementUpdate(int id, String field, Connection connection, String query) throws DaoException {
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

    public boolean remove(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(REMOVE_ACCOUNT)) {
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            this.connection.commit();
            close();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void close() {
        connectionPool.returnConnection(connection);
    }

    private boolean insertRowInAccountAndAccountInfo(String username, String password, String firstName, String lastName,
                                                     String middleName, String birthday, String phonePers, String phoneWork,
                                                     String addressPers, String addressWork, String email, int icq, String skype,
                                                     String extra, InputStream photo, String photoFileName) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_NEW_ACCOUNT)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            int accountId = -1;
            try (PreparedStatement preparedStatementId = this.connection.prepareStatement(SELECT_ACCOUNT_ID_FROM_ACCOUNTS)) {
                preparedStatementId.setString(1, username);
                try (ResultSet resultSet = preparedStatementId.executeQuery()) {
                    if (resultSet.next()) {
                        accountId = resultSet.getInt(COLUMN_ACCOUNT_ID);
                    }
                }
            }
            try (PreparedStatement preparedStatementInsert = this.connection.prepareStatement(INSERT_NEW_ACCOUNT_INFO)) {
                preparedStatementInsert.setInt(1, accountId);
                preparedStatementInsert.setString(2, firstName);
                preparedStatementInsert.setString(3, lastName);
                preparedStatementInsert.setString(4, middleName);
                preparedStatementInsert.setString(5, birthday);
                preparedStatementInsert.setString(6, phonePers);
                preparedStatementInsert.setString(7, phoneWork);
                preparedStatementInsert.setString(8, addressPers);
                preparedStatementInsert.setString(9, addressWork);
                preparedStatementInsert.setString(10, email);
                preparedStatementInsert.setInt(11, icq);
                preparedStatementInsert.setString(12, skype);
                preparedStatementInsert.setString(13, extra);
                preparedStatementInsert.setBlob(14, photo);
                preparedStatementInsert.setString(15, photoFileName);
                preparedStatementInsert.executeUpdate();
                this.connection.commit();
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Account createAccountFromResult(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt(COLUMN_ACCOUNT_ID));
        account.setUsername(resultSet.getString("username"));
        account.setPassword(resultSet.getString("password"));
        account.setFirstName(resultSet.getString("first_name"));
        account.setLastName(resultSet.getString("last_name"));
        account.setMiddleName(resultSet.getString("middle_name"));
        account.setBirthday(resultSet.getString("birthday"));
        account.setPhonePers(resultSet.getString("phone_pers"));
        account.setPhoneWork(resultSet.getString("phone_work"));
        account.setAddressPers(resultSet.getString("address_pers"));
        account.setAddressWork(resultSet.getString("address_work"));
        account.setEmail(resultSet.getString("email"));
        account.setIcq(resultSet.getInt("icq"));
        account.setSkype(resultSet.getString("skype"));
        account.setExtra(resultSet.getString("extra"));
        account.setPhoto(resultSet.getBytes("photo"));
        account.setPhotoFileName(resultSet.getString("photo_file_name"));
        return account;
    }
}