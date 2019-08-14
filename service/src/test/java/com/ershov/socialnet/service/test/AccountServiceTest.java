package com.ershov.socialnet.service.test;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

//    @Mock
//    private AccountDAO accountDAO;
//
//    @InjectMocks
//    private AccountService accountService;
//
//    @Test
//    public void createTest() throws DaoNameException {
//        List<Phone> phones = new ArrayList<>();
//        phones.add(new Phone(1, "800", PhoneType.MOBILE));
//        phones.add(new Phone(2, "801", PhoneType.WORK));
//        Account account = new Account(0, "kolya1@mail", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "1982-12-13", null, "dddd", "1111",
//                "2018-06-13", Role.ROLE_USER, phones);
//        when(accountDAO.create(account)).thenReturn(true);
//        assertTrue(accountService.create(account));
//    }
//
//    @Test(expected = DaoNameException.class)
//    public void createExceptionTest() throws DaoNameException {
//        List<Phone> phones = new ArrayList<>();
//        phones.add(new Phone(1, "800", PhoneType.MOBILE));
//        phones.add(new Phone(2, "801", PhoneType.WORK));
//        Account account = new Account(0, "kolya1@mail", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "1982-12-13", null, "dddd", "1111",
//                "2018-06-13", Role.ROLE_USER, phones);
//        when(accountDAO.create(account)).thenThrow(new DaoNameException());
//        accountService.create(account);
//    }
//
//    @Test
//    public void getTest() {
//        List<Phone> phones = new ArrayList<>();
//        phones.add(new Phone(1, "900", PhoneType.MOBILE));
//        phones.add(new Phone(2, "901", PhoneType.WORK));
//        Account account = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
//                "Urievich", "1988-07-22", null, "aaaaa", "0",
//                "2018-06-08", Role.ROLE_ADMIN, phones);
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
//        List<Phone> phones = new ArrayList<>();
//        phones.add(new Phone(1, "900", PhoneType.MOBILE));
//        phones.add(new Phone(2, "901", PhoneType.WORK));
//        Account account1 = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
//                "Urievich", "1988-07-22", null, "aaaaa", "0", "2018-06-08",
//                Role.ROLE_ADMIN, phones);
//        Account account2 = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
//                null, "1990-01-01", null, "bbbbb", "0", "2018-06-13", Role.ROLE_USER,
//                new ArrayList<>());
//        List<Account> accounts = new ArrayList<>();
//        accounts.add(account1);
//        accounts.add(account2);
//        when(accountDAO.searchByString("ey", 1)).thenReturn(accounts);
//        assertEquals(accounts, accountService.searchByStringPages("ey", 1));
//    }
//
//    @Test
//    public void getRoleTest() {
//        when(accountDAO.getRole(1)).thenReturn(Role.ROLE_ADMIN);
//        assertEquals(Role.ROLE_ADMIN, accountService.getRole(1));
//    }
//
//    @Test
//    public void updateTest() {
//        Account account = new Account(1, "a@a.ru", null, "Ivan", null, null,
//                "", null, null, "0", "2018-06-13", Role.ROLE_USER, null);
//        when(accountDAO.update(account)).thenReturn(true);
//        when(accountDAO.get(1)).thenReturn(account);
//        assertTrue(accountService.update(account));
//    }
//
//    @Test
//    public void updateRoleTest() {
//        when(accountDAO.updateRole(1, Role.ROLE_ADMIN)).thenReturn(true);
//        assertTrue(accountService.updateRole(1, Role.ROLE_ADMIN));
//    }
//
//    @Test
//    public void removeTest() {
//        when(accountDAO.remove(1)).thenReturn(true);
//        assertTrue(accountService.remove(1));
//    }
}