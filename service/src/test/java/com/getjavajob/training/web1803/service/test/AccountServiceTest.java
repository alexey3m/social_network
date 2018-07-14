package com.getjavajob.training.web1803.service.test;

import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.service.AccountService;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountDAO accountDAO;
    @Mock
    private PhoneDAO phoneDAO;

    @InjectMocks
    private AccountService accountService;

//    @Test
//    public void createTest() throws DaoNameException {
//        when(accountDAO.create("kol@ya", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
//                "2018-06-13", Role.USER)).thenReturn(true);
//        when(accountDAO.getId("kol@ya")).thenReturn(1);
//        when(phoneDAO.create(1, "900", PhoneType.MOBILE)).thenReturn(true);
//        when(phoneDAO.create(1, "901", PhoneType.WORK)).thenReturn(true);
//        Map<String, PhoneType> phones = new HashMap<>();
//        phones.put("900", PhoneType.MOBILE);
//        phones.put("901", PhoneType.WORK);
//        assertTrue(accountService.create("kol@ya", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
//                "2018-06-13", Role.USER, phones));
//    }
//
//    @Test(expected = DaoNameException.class)
//    public void createExceptionTest() throws DaoNameException {
//        when(accountDAO.create("kol@ya", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
//                "2018-06-13", Role.USER)).thenThrow(new DaoNameException());
//        Map<String, PhoneType> phones = new HashMap<>();
//        phones.put("900", PhoneType.MOBILE);
//        phones.put("901", PhoneType.WORK);
//        accountService.create("kol@ya", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
//                "2018-06-13", Role.USER, phones);
//    }
//
//    @Test
//    public void getTest() {
//        Map<String, PhoneType> phones = new HashMap<>();
//        phones.put("900", PhoneType.MOBILE);
//        phones.put("901", PhoneType.WORK);
//        Account account = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
//                "Urievich", "1988-07-22", null, null, "aaaaa", 0,
//                "2018-06-08", Role.ADMIN, phones);
//        when(accountDAO.get(1)).thenReturn(account);
//        assertEquals(account, accountService.get(1));
//    }
//
//    @Test
//    public void loginAndGetIdTest() throws DaoNameException {
//        when(accountDAO.loginAndGetId("a@a.ru", "123")).thenReturn(1);
//        assertEquals(1, accountService.loginAndGetId("a@a.ru", "123"));
//    }
//
//    @Test(expected = DaoNameException.class)
//    public void loginAndGetIdExceptionTest() throws DaoNameException {
//        when(accountDAO.loginAndGetId("a@a.ru", "")).thenThrow(new DaoNameException());
//        assertEquals(1, accountService.loginAndGetId("a@a.ru", ""));
//    }
//
//    @Test
//    public void searchByStringTest() {
//        Map<String, PhoneType> phones = new HashMap<>();
//        phones.put("900", PhoneType.MOBILE);
//        phones.put("901", PhoneType.WORK);
//        Account account1 = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
//                "Urievich", "1988-07-22", null, null, "aaaaa", 0,
//                "2018-06-08", Role.ADMIN, phones);
//        Account account2 = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
//                null, "1990-01-01", null, null, "bbbbb", 0,
//                "2018-06-13", Role.USER, new HashMap<>());
//        List<Account> accounts = new ArrayList<>();
//        accounts.add(account1);
//        accounts.add(account2);
//        when(accountDAO.searchByString("ey")).thenReturn(accounts);
//        assertEquals(accounts, accountService.searchByString("ey"));
//    }
//
//    @Test
//    public void getRoleTest() {
//        when(accountDAO.getRole(1)).thenReturn(Role.ADMIN);
//        assertEquals(Role.ADMIN, accountService.getRole(1));
//    }
//
//    @Test
//    public void getIdTest() {
//        when(accountDAO.getId("a@a.ru")).thenReturn(1);
//        assertEquals(1, accountService.getId("a@a.ru"));
//    }
//
//    @Test
//    public void updateTest() {
//        when(accountDAO.getId("a@a.ru")).thenReturn(1);
//        Account account = new Account(1, "a@a.ru", null, "Ivan", null, null,
//                null, null, null, null, 0, null, null, null);
//        when(accountDAO.update(account)).thenReturn(true);
//        assertTrue(accountService.update("a@a.ru", null, "Ivan", null, null, "",
//                null, null, null, 0, null));
//    }
//
//    @Test
//    public void updateRoleTest() {
//        when(accountDAO.updateRole(1, Role.ADMIN)).thenReturn(true);
//        assertTrue(accountService.updateRole(1, Role.ADMIN));
//    }
//
//    @Test
//    public void removeTest() {
//        when(accountDAO.remove(1)).thenReturn(true);
//        assertTrue(accountService.remove(1));
//    }
}