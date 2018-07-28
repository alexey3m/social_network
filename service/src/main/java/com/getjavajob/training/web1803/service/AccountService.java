package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountService {
    private AccountDAO accountDAO;


    @Autowired
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Transactional
    public boolean create(Account account) throws DaoNameException {
        String birthday = account.getBirthday();
        account.setBirthday(birthday.length() == 0 ? null : birthday);
        accountDAO.create(account);
        return true;
    }

    public Account get(int id) {
        return accountDAO.get(id);
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
        return accountDAO.searchByString(search);
    }

    public Role getRole(int accountId) {
        return accountDAO.getRole(accountId);
    }

    public boolean update(Account account) {
        Account currentAccount = accountDAO.get(account.getId());
        String birthday = account.getBirthday();
        account.setBirthday(birthday.equals("") ? null : birthday);
        account.setRole(currentAccount.getRole());
        account.setRegDate(currentAccount.getRegDate());
        accountDAO.update(account);
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