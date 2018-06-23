package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.PhoneType;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.util.Map;

public class PhoneService {
    private PhoneDAO phoneDAO;

    public PhoneService() {
        try {
            phoneDAO = new PhoneDAO();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    //Constructor for tests
    public PhoneService(PhoneDAO phoneDAO) {
        this.phoneDAO = phoneDAO;
    }

    public boolean create(int accountId, String number, PhoneType type) {
        try {
            return phoneDAO.create(accountId, number, type);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Map<PhoneNumber, PhoneType>
    public Map<String, PhoneType> getAll(int accountId) {
        try {
            return phoneDAO.getAll(accountId);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean update(int accountId, Map<String, PhoneType> phones) {
        try {
            return phoneDAO.update(accountId, phones);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int accountId) {
        try {
            return phoneDAO.remove(accountId);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }
}