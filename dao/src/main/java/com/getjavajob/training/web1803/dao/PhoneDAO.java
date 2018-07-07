package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
    public boolean create(int accountId, String number, PhoneType type) {
        int result = this.jdbcTemplate.update(INSERT_PHONE, accountId, number, type.getStatus());
        return result != 0;
    }

    // Map<PhoneNumber, PhoneType>
    public Map<String, PhoneType> getAll(int accountId) {
        return this.jdbcTemplate.query(SELECT_PHONES_BY_ACCOUNT_ID, new Object[]{accountId},
                rs -> {
                    Map<String, PhoneType> result = new HashMap<>();
                    while (rs.next()) {
                        result.put(rs.getString("phone_number"), PhoneType.values()[rs.getInt("phone_type")]);
                    }
                    return result;
                });
    }

    // Map<PhoneNumber, PhoneType>
    public boolean update(int accountId, Map<String, PhoneType> phones) {
        remove(accountId);
        boolean result = false;
        for (Entry<String, PhoneType> phone : phones.entrySet()) {
            result = create(accountId, phone.getKey(), phone.getValue());
        }
        return result;
    }

    @Transactional
    public boolean remove(int accountId) {
        int result = this.jdbcTemplate.update(REMOVE_PHONES, accountId);
        return result != 0;
    }
}