package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Phone;
import com.getjavajob.training.web1803.common.enums.PhoneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PhoneDAO {
    private static final String SELECT_PHONES_BY_ACCOUNT_ID = "SELECT * FROM phone WHERE account_id = ?";
    private static final String INSERT_PHONE = "INSERT INTO phone (account_id, phone_number, phone_type) VALUES (?, ?, ?)";
    private static final String REMOVE_PHONES = "DELETE FROM phone WHERE account_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PhoneDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public boolean create(Account account) {
        int result = 0;
        for (Phone phone : account.getPhones()) {
            result = this.jdbcTemplate.update(INSERT_PHONE, account.getId(), phone.getNumber(), phone.getPhoneType());
        }
        return result != 0;
    }

    public List<Phone> getAll(int accountId) {
        return this.jdbcTemplate.query(SELECT_PHONES_BY_ACCOUNT_ID, new Object[]{accountId},
                rs -> {
                    List<Phone> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(new Phone(rs.getInt("phone_id"), rs.getString("phone_number"), PhoneType.values()[rs.getInt("phone_type")]));
                    }
                    return result;
                });
    }

    public boolean update(Account account) {
        remove(account.getId());
        boolean result = true;
        if (account.getPhones() != null) {
            result = create(account);
        }
        return result;
    }

    @Transactional
    public boolean remove(int accountId) {
        int result = this.jdbcTemplate.update(REMOVE_PHONES, accountId);
        return result != 0;
    }
}