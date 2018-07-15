package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private AccountDAO accountDAO;
    private PhoneDAO phoneDAO;

    @Autowired
    public AccountService(AccountDAO accountDAO, PhoneDAO phoneDAO) {
        this.accountDAO = accountDAO;
        this.phoneDAO = phoneDAO;
    }

    public boolean create(Account account) throws DaoNameException {
        String birthday = account.getBirthday();
        account.setBirthday(birthday.length() == 0 ? null : birthday);
        accountDAO.create(account);
        account.setId(accountDAO.getId(account.getEmail()));
        phoneDAO.create(account);
        return true;
    }

    public Account get(int id) {
        Account result = accountDAO.get(id);
        if (result != null) {
            result.setPhones(phoneDAO.getAll(id));
        }
        return result;
    }

    public byte[] getPhoto(int id) {
        byte[] photo = accountDAO.getPhoto(id);
        if (photo != null) {
            return photo.length == 0 ? null : photo;
        } else {
            return null;
        }
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

    public boolean update(Account account) {
        accountDAO.update(account);
        phoneDAO.update(account);
        return true;
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