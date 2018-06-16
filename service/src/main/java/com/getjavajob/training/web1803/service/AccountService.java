package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        try {
            accountDAO = new AccountDAO();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    //Constructor for tests
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }


    public boolean create(String email, String password, String firstName, String lastName, String middleName,
                          String birthday, InputStream photo, String photoFileName, String skype, int icq, String regDate,
                          Role role) throws DaoNameException {
        try {
            return accountDAO.create(email, password, firstName, lastName, middleName, birthday, photo, photoFileName,
                    skype, icq, regDate, role);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Account get(int id) {
        try {
            return accountDAO.get(id);
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

    public List<Account> getAll() {
        try {
            return accountDAO.getAll();
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
        } catch (DaoException | DaoNameException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean update(String email, String password, String firstName, String lastName, String middleName, String birthday,
                          InputStream photo, String photoFileName, String skype, int icq) {
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
            account.setPhoto(IOUtils.toByteArray(photo));
            account.setPhotoFileName(photoFileName);
            account.setSkype(skype);
            account.setIcq(icq);
            return accountDAO.update(account);
        } catch (DaoException | IOException | DaoNameException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(String email, Role role) {
        try {
            int id = accountDAO.getId(email);
            return accountDAO.updateRole(id, role);
        } catch (DaoException | DaoNameException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(int id) {
        try {
            return accountDAO.remove(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return false;
        }
    }
}