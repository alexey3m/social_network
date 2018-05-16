import exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private static final String SELECT_ALL_FROM_ACCOUNTS = "SELECT * FROM accounts WHERE username = ?";
    private static final String SELECT_ALL_ACCOUNTS_INFO = "SELECT a.account_id, username, password, " +
            "first_name, last_name, middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, " +
            "icq, skype, extra FROM accounts a JOIN account_info ai ON a.account_id = ai.account_id";
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
    private static final String REMOVE_ACCOUNT = "DELETE FROM accounts WHERE account_id = ?";
    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO accounts (username, password) VALUES (?, ?)";
    private static final String SELECT_ACCOUNT_ID_FROM_ACCOUNTS = "SELECT account_id FROM accounts WHERE username = ?";
    private static final String INSERT_NEW_ACCOUNT_INFO = "INSERT INTO account_info (account_id, first_name, last_name, " +
            "middle_name, birthday, phone_pers, phone_work, address_pers, address_work, email, icq, skype, extra) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String COLUMN_ACCOUNT_ID = "account_id";
    private Connection connection;
    private ConnectionPool connectionPool;

    public AccountDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
        connection = connectionPool.getConnection();
    }

    // Constructor for tests
    public AccountDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean create(String username, String password, String firstName, String lastName, String middleName,
                          String birthday, String phonePers, String phoneWork, String addressPers, String addressWork,
                          String email, int icq, String skype, String extra) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_FROM_ACCOUNTS)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    insertRowInAccountAndAccountInfo(username, password, firstName, lastName, middleName, birthday, phonePers,
                            phoneWork, addressPers, addressWork, email, icq, skype, extra);
                    return true;
                } else {
                    throw new DaoException("Username \"" + username + "\" is already used.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Account get(int id) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ACCOUNT_INFO)) {
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

    public int getId(String username) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ALL_FROM_ACCOUNTS)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(COLUMN_ACCOUNT_ID);
                } else {
                    throw new DaoException("Username \"" + username + "\" not found in database.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Account> getAll() throws DaoException {
        try (ResultSet resultSet = this.connection.createStatement().executeQuery(SELECT_ALL_ACCOUNTS_INFO)) {
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
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_PASS)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Account account) throws DaoException {
        int id = account.getId();
        String firstName = account.getFirstName();
        if (firstName != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_FIRST_NAME)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String lastName = account.getLastName();
        if (lastName != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_LAST_NAME)) {
                preparedStatement.setString(1, lastName);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String middleName = account.getMiddleName();
        if (middleName != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_MIDDLE_NAME)) {
                preparedStatement.setString(1, middleName);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String birthday = account.getBirthday();
        if (birthday != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_BIRTHDAY)) {
                preparedStatement.setString(1, birthday);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String phonePers = account.getPhonePers();
        if (phonePers != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_PHONE_PERS)) {
                preparedStatement.setString(1, phonePers);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String phoneWork = account.getPhoneWork();
        if (phoneWork != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_PHONE_WORK)) {
                preparedStatement.setString(1, phoneWork);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String addressPers = account.getAddressPers();
        if (addressPers != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_ADDRESS_PERS)) {
                preparedStatement.setString(1, addressPers);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String addressWork = account.getAddressWork();
        if (addressWork != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_ADDRESS_WORK)) {
                preparedStatement.setString(1, addressWork);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String email = account.getEmail();
        if (email != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_EMAIL)) {
                preparedStatement.setString(1, email);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
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
        String skype = account.getSkype();
        if (skype != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_SKYPE)) {
                preparedStatement.setString(1, skype);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        String extra = account.getExtra();
        if (extra != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ACCOUNT_INFO_SET_EXTRA)) {
                preparedStatement.setString(1, extra);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        try {
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean remove(int id) throws DaoException {
        try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(REMOVE_ACCOUNT)) {
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void closeConnection() {
        connectionPool.returnConnection(connection);
    }

    private boolean insertRowInAccountAndAccountInfo(String username, String password, String firstName, String lastName,
                                                     String middleName, String birthday, String phonePers, String phoneWork,
                                                     String addressPers, String addressWork, String email, int icq, String skype,
                                                     String extra) throws DaoException {
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
        return account;
    }
}