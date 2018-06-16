import com.getjavajob.training.web1803.dao.AccountDAO;

import static org.mockito.Mockito.mock;

public class AccountServiceTest {

    private AccountDAO accountDAO = mock(AccountDAO.class);
//
//    @InjectMocks
//    private AccountService accountService = new AccountService(accountDAO);
//
//    @Test
//    public void createTest() throws DaoException, DaoNameException {
//        when(accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
//                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-", null, null)).thenReturn(true);
//        assertTrue(accountService.create("kolya1", "123", "Nikolay", "Malcev",
//                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
//                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-", null, null));
//    }
//
//    @Test
//    public void updateInfoTest() throws DaoException {
//        when(accountDAO.getId("alex1")).thenReturn(1);
//        Account account = new Account(1, "alex1", null, "Ivan", null, null,
//                null, null, null, null, null, null, 0, null, null, null, null);
//        when(accountDAO.update(account)).thenReturn(true);
//
//        assertTrue(accountService.update("alex1", "Ivan", null, null, null,
//                null, null, null, null, null, 0, null, null, null, null));
//    }
//
//    @Test
//    public void updatePasswordTest() throws DaoException {
//        when(accountDAO.getId("alex1")).thenReturn(1);
//        when(accountDAO.updatePassword(1, "321")).thenReturn(true);
//
//        assertTrue(accountService.updatePassword("alex1", "321"));
//    }
//
//    @Test
//    public void removeByUsernameTest() throws DaoException {
//        when(accountDAO.getId("alex1")).thenReturn(1);
//        when(accountDAO.remove(1)).thenReturn(true);
//
//        assertTrue(accountService.remove("alex1"));
//    }
//
//    @Test
//    public void removeByIdTest() throws DaoException {
//        when(accountDAO.remove(1)).thenReturn(true);
//        assertTrue(accountService.remove(1));
//    }
//
//    @Test
//    public void getTest() throws DaoException {
//        Account account = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
//                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
//                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information", null, null);
//        when(accountDAO.get(1)).thenReturn(account);
//        assertEquals(account, accountService.get(1));
//    }
//
//    @Test
//    public void getAllTest() throws DaoException {
//        Account account1 = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
//                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
//                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information", null, null);
//        Account account2 = new Account(2, "ivan1", "1234", "Ivan", "Ivanov", "Alexeyevich",
//                "27.01.1993", "+79230000001", "+739121231212", "Moskow", "Moskow 2",
//                "a2@a.ru", 1234500000, "bbbb", "extra information", null, null);
//        Account account3 = new Account(3, "sergey1", "12345", "Sergey", "Nosov", "Ivanovych",
//                "02.09.1987", "+79230000002", "+739121231212", "Sankt Petersburg", "Sankt Petersburg 2",
//                "a3@a.ru", 1234500001, "ccccc", "extra information", null, null);
//        Account account4 = new Account(4, "ivan2", "123456", "Ivan", "Menshov", "Maksimovich",
//                "19.01.1981", "+79230000003", "+739121231212", "Novosibirsk 1", "Novosibirsk 2",
//                "cc@c.ru", 1234500002, "dddd", "extra information", null, null);
//        List<Account> accounts = new ArrayList<>();
//        accounts.add(account1);
//        accounts.add(account2);
//        accounts.add(account3);
//        accounts.add(account4);
//        when(accountDAO.getAll()).thenReturn(accounts);
//        assertEquals(accounts, accountService.getAll());
//    }
}