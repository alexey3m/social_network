package com.getjavajob.training.web1803.service.test;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.Pool;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import com.getjavajob.training.web1803.service.AccountService;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    private AccountDAO accountDAO = mock(AccountDAO.class);
    private PhoneDAO phoneDAO = mock(PhoneDAO.class);
    private Pool connectionPool = mock(Pool.class);

    @InjectMocks
    private AccountService accountService = new AccountService(accountDAO, phoneDAO, connectionPool);

    @Test
    public void createTest() throws DaoException, DaoNameException {
        when(accountDAO.create("kol@ya", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER)).thenReturn(true);
        when(accountDAO.getId("kol@ya")).thenReturn(1);
        when(phoneDAO.create(1, "900", PhoneType.HOME)).thenReturn(true);
        when(phoneDAO.create(1, "901", PhoneType.WORK)).thenReturn(true);
        Map<String, PhoneType> phones = new HashMap<>();
        phones.put("900", PhoneType.HOME);
        phones.put("901", PhoneType.WORK);
        assertTrue(accountService.create("kol@ya", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER, phones));
    }

    @Test(expected = DaoNameException.class)
    public void createExceptionTest() throws DaoException, DaoNameException {
        when(accountDAO.create("kol@ya", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER)).thenThrow(new DaoNameException());
        when(accountDAO.getId("kol@ya")).thenReturn(1);
        when(phoneDAO.create(1, "900", PhoneType.HOME)).thenReturn(true);
        when(phoneDAO.create(1, "901", PhoneType.WORK)).thenReturn(true);
        Map<String, PhoneType> phones = new HashMap<>();
        phones.put("900", PhoneType.HOME);
        phones.put("901", PhoneType.WORK);
        accountService.create("kol@ya", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER, phones);
    }

    @Test
    public void getTest() throws DaoException {
        Map<String, PhoneType> phones = new HashMap<>();
        phones.put("900", PhoneType.HOME);
        phones.put("901", PhoneType.WORK);
        Account account = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Urievich", "1988-07-22", null, null, "aaaaa", 0,
                "2018-06-08", Role.ADMIN, phones);
        when(accountDAO.get(1)).thenReturn(account);
        assertEquals(account, accountService.get(1));
    }

    @Test
    public void loginAndGetIdTest() throws DaoException, DaoNameException {
        when(accountDAO.loginAndGetId("a@a.ru", "123")).thenReturn(1);
        assertEquals(1, accountService.loginAndGetId("a@a.ru", "123"));
    }

    @Test(expected = DaoNameException.class)
    public void loginAndGetIdExceptionTest() throws DaoException, DaoNameException {
        when(accountDAO.loginAndGetId("a@a.ru", "")).thenThrow(new DaoNameException());
        assertEquals(1, accountService.loginAndGetId("a@a.ru", ""));
    }

    @Test
    public void searchByStringTest() throws DaoException {
        Map<String, PhoneType> phones = new HashMap<>();
        phones.put("900", PhoneType.HOME);
        phones.put("901", PhoneType.WORK);
        Account account1 = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Urievich", "1988-07-22", null, null, "aaaaa", 0,
                "2018-06-08", Role.ADMIN, phones);
        Account account2 = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
                null, "1990-01-01", null, null, "bbbbb", 0,
                "2018-06-13", Role.USER, new HashMap<>());
        List<Account> accounts = new ArrayList<>();
        accounts.add(account1);
        accounts.add(account2);
        when(accountDAO.searchByString("ey")).thenReturn(accounts);
        assertEquals(accounts, accountService.searchByString("ey"));
    }

    @Test
    public void getRoleTest() throws DaoException {
        when(accountDAO.getRole(1)).thenReturn(Role.ADMIN);
        assertEquals(Role.ADMIN, accountService.getRole(1));
    }

    @Test
    public void getIdTest() throws DaoException {
        when(accountDAO.getId("a@a.ru")).thenReturn(1);
        assertEquals(1, accountService.getId("a@a.ru"));
    }

    @Test
    public void updateTest() throws DaoException {
        when(accountDAO.getId("a@a.ru")).thenReturn(1);
        Account account = new Account(1, "a@a.ru", null, "Ivan", null, null,
                null, null, null, null, 0, null, null, null);
        when(accountDAO.update(account)).thenReturn(true);
        assertTrue(accountService.update("a@a.ru", null, "Ivan", null, null, null,
                null, null, null, 0, null));
    }

    @Test
    public void updateRoleTest() throws DaoException {
        when(accountDAO.updateRole(1, Role.ADMIN)).thenReturn(true);
        assertTrue(accountService.updateRole(1, Role.ADMIN));
    }

    @Test
    public void removeTest() throws DaoException {
        when(accountDAO.remove(1)).thenReturn(true);
        assertTrue(accountService.remove(1));
    }
}