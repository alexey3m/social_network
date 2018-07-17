package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDAO {
    private static final String SELECT_ALL_ACCOUNTS = "SELECT * FROM account";
    private static final String SEARCH_ACCOUNTS_BY_STRING = "SELECT * FROM (SELECT *, CONCAT(first_name, " +
            "IF (middle_name = '', '', CONCAT(' ', middle_name)), IF (last_name = '', '', CONCAT(' ', last_name))) AS full_name FROM account) p " +
            "WHERE full_name LIKE ?";
    private static final String SELECT_ACCOUNT_BY_ID = SELECT_ALL_ACCOUNTS + " WHERE id = ?";
    private static final String SELECT_PHOTO_BY_ID = "SELECT photo FROM account WHERE id = ?";
    private static final String SELECT_ACCOUNT_BY_EMAIL = SELECT_ALL_ACCOUNTS + " WHERE email = ?";
    private static final String CHECK_EMAIL_AND_PASSWORD = SELECT_ACCOUNT_BY_EMAIL + " AND password = ?";
    private static final String INSERT_NEW_ACCOUNT = "INSERT INTO account (email, password, first_name, last_name, middle_name, " +
            "birthday, photo, skype, icq, reg_date, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ACCOUNT = "UPDATE account SET password = ?, first_name = ?, last_name = ?, middle_name = ?, " +
            "birthday = ?, photo = ?, skype = ?, icq = ? WHERE id = ?";
    private static final String UPDATE_ACCOUNT_SET_ROLE = "UPDATE account SET role = ? WHERE id = ?";
    private static final String COLUMN_ACCOUNT_ID = "id";
    private static final String REMOVE_ACCOUNT = "DELETE FROM account WHERE id = ?";
    private static final String SELECT_ROLE = "SELECT role FROM account WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Account account) throws DaoNameException {
        String email = account.getEmail().trim();
        boolean emailExist = this.jdbcTemplate.query(SELECT_ACCOUNT_BY_EMAIL, new Object[]{email}, ResultSet::next);
        if (!emailExist) {
            int result = this.jdbcTemplate.update(INSERT_NEW_ACCOUNT, email, account.getPassword().trim(),
                    account.getFirstName().trim(), account.getLastName().trim(), account.getMiddleName().trim(), account.getBirthday(),
                    account.getPhoto(), account.getSkype(), account.getIcq(), account.getRegDate(), account.getRole().getStatus());
            return result != 0;
        } else {
            throw new DaoNameException("Email \"" + email + "\" is already used.");
        }
    }

    public Account get(int id) {
        return this.jdbcTemplate.query(SELECT_ACCOUNT_BY_ID, new Object[]{id}, rs -> {
            Account account = new Account();
            if (rs.next()) {
                account = createAccountFromResult(rs);
            }
            return account;
        });
    }

    public byte[] getPhoto(int id) {
        return this.jdbcTemplate.queryForObject(SELECT_PHOTO_BY_ID, new Object[]{id}, (rs, rowNum) -> rs.getBytes("photo"));
    }

    public int getId(String email) {
        return this.jdbcTemplate.queryForObject(SELECT_ACCOUNT_BY_EMAIL, new Object[]{email},
                (rs, rowNum) -> rs.getInt(COLUMN_ACCOUNT_ID));
    }

    public int loginAndGetId(String email, String password) throws DaoNameException {
        int result = this.jdbcTemplate.query(CHECK_EMAIL_AND_PASSWORD, new Object[]{email, password},
                rs -> rs.next() ? rs.getInt("id") : 0);
        if (result == 0) {
            throw new DaoNameException("Email: \"" + email + "\" and password: " + password + " not found in database.");
        } else {
            return result;
        }
    }

    public Role getRole(int accountId) {
        return this.jdbcTemplate.queryForObject(SELECT_ROLE, new Object[]{accountId},
                (rs, rowNum) -> Role.values()[rs.getInt("role")]);
    }

    public List<Account> searchByString(String search) {
        String lowerSearch = search.toLowerCase();
        return this.jdbcTemplate.query(SEARCH_ACCOUNTS_BY_STRING, new Object[]{"%" + lowerSearch + "%"}, rs -> {
            List<Account> result = new ArrayList<>();
            while (rs.next()) {
                result.add(createAccountFromResult(rs));
            }
            return result;
        });
    }

    @Transactional
    public boolean update(Account account) {
        String birthday = account.getBirthday();
        int result = this.jdbcTemplate.update(UPDATE_ACCOUNT, account.getPassword(), account.getFirstName(),
                account.getLastName(), account.getMiddleName(), birthday.equals("") ? null : birthday, account.getPhoto(),
                account.getSkype(), account.getIcq(), account.getId());
        return result != 0;
    }

    @Transactional
    public boolean updateRole(int accountId, Role newRole) {
        int result = this.jdbcTemplate.update(UPDATE_ACCOUNT_SET_ROLE, newRole.getStatus(), accountId);
        return result != 0;
    }

    @Transactional
    public boolean remove(int id) {
        int result = this.jdbcTemplate.update(REMOVE_ACCOUNT, id);
        return result != 0;
    }

    private Account createAccountFromResult(ResultSet resultSet) throws SQLException {
        Account account = new Account();
        account.setId(resultSet.getInt(COLUMN_ACCOUNT_ID));
        account.setEmail(resultSet.getString("email"));
        account.setPassword(resultSet.getString("password"));
        account.setFirstName(resultSet.getString("first_name"));
        account.setLastName(resultSet.getString("last_name"));
        account.setMiddleName(resultSet.getString("middle_name"));
        account.setBirthday(resultSet.getString("birthday"));
        account.setPhoto(resultSet.getBytes("photo"));
        account.setSkype(resultSet.getString("skype"));
        account.setIcq(resultSet.getInt("icq"));
        account.setRegDate(resultSet.getString("reg_date"));
        account.setRole(Role.values()[resultSet.getInt("role")]);
        return account;
    }
}