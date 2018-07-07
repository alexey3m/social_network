package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private AccountDAO accountDAO;
    private PhoneDAO phoneDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO) {
        this.accountDAO = accountDAO;
        this.phoneDAO = phoneDAO;
    }

    public boolean create(String email, String password, String firstName, String lastName, String middleName,
                          String birthday, InputStream photo, String photoFileName, String skype, int icq, String regDate,
                          Role role, Map<String, PhoneType> phones) throws DaoNameException {
        accountDAO.create(email, password, firstName, lastName, middleName, birthday, photo, photoFileName,
                skype, icq, regDate, role);
        int id = accountDAO.getId(email);
        for (Map.Entry<String, PhoneType> phone : phones.entrySet()) {
            phoneDAO.create(id, phone.getKey(), phone.getValue());
        }
        return true;
    }

    public Account get(int id) {
        Account result = accountDAO.get(id);
        result.setPhones(phoneDAO.getAll(id));
        return result;
    }

    public int loginAndGetId(String email, String password) throws DaoNameException {
        return accountDAO.loginAndGetId(email, password);
    }

    public List<Account> searchByString(String search) {
        List<Account> result = accountDAO.searchByString(search);
        for (Account account : result) {
            account.setPhones(phoneDAO.getAll(account.getId()));
        }
        return result;
    }

    public Role getRole(int accountId) {
        return accountDAO.getRole(accountId);
    }

    public int getId(String email) {
        return accountDAO.getId(email);
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
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateRole(int id, Role role) {
        accountDAO.updateRole(id, role);
        return true;
    }

    public boolean remove(int id) {
        accountDAO.remove(id);
        return true;
    }
}