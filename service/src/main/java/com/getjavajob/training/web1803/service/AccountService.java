package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AccountService {
    private AccountDAO accountDAO;
    private PhoneDAO phoneDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
        phoneDAO = new PhoneDAO();
    }

    //Constructor for tests
    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO) {
        this.accountDAO = accountDAO;
        this.phoneDAO = phoneDAO;
    }

    public boolean create(String email, String password, String firstName, String lastName, String middleName,
                          String birthday, InputStream photo, String photoFileName, String skype, int icq, String regDate,
                          Role role, Map<String, PhoneType> phones) throws DaoNameException {
        try {
            accountDAO.create(email, password, firstName, lastName, middleName, birthday, photo, photoFileName,
                    skype, icq, regDate, role);
            int id = accountDAO.getId(email);
            for (Map.Entry<String, PhoneType> phone : phones.entrySet()) {
                phoneDAO.create(id, phone.getKey(), phone.getValue());
            }
            return true;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account get(int id) {
        try {
            Account result = accountDAO.get(id);
            result.setPhones(phoneDAO.getAll(id));
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int loginAndGetId(String email, String password) throws DaoNameException {
        try {
            return accountDAO.loginAndGetId(email, password);
        } catch (DaoException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Account> searchByString(String search) {
        try {
            List<Account> result = accountDAO.searchByString(search);
            for (Account account : result) {
                account.setPhones(phoneDAO.getAll(account.getId()));
            }
            return result;
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Role getRole(int accountId) {
        try {
            return accountDAO.getRole(accountId);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getId(String email) {
        try {
            return accountDAO.getId(email);
        } catch (DaoException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean update(String email, String password, String firstName, String lastName, String middleName, String birthday,
                          InputStream photo, String photoFileName, String skype, int icq, Map<String, PhoneType> phones) {
        try {
            int id = accountDAO.getId(email);
            Account account = new Account();
            account.setId(id);
            account.setEmail(email);
            account.setPassword(password);
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setMiddleName(middleName);
            account.setBirthday(birthday);
            account.setPhoto(photo != null ? IOUtils.toByteArray(photo) : null);
            account.setPhotoFileName(photoFileName);
            account.setSkype(skype);
            account.setIcq(icq);
            accountDAO.update(account);
            phoneDAO.update(id, phones);
            return true;
        } catch (DaoException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(int id, Role role) {
        try {
            accountDAO.updateRole(id, role);
            return true;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        try {
            accountDAO.remove(id);
            return true;
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }
}