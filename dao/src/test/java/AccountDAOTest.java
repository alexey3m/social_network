import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountDAOTest {
    private ConnectionPool connectionPool;
    private PhoneDAO phoneDAO;

    @Before
    public void initDB() throws IOException, SQLException {
        connectionPool = new ConnectionPool();
        phoneDAO = new PhoneDAO(connectionPool);
        ScriptRunnerUtil runner = new ScriptRunnerUtil(connectionPool.getConnection(), true, true);
        runner.runScript(new BufferedReader(new FileReader("c:/java/dev/projects/getjavajob/social-network-app/dao/src/test/resources/create-data-model.sql")));
        runner.runScript(new BufferedReader(new FileReader("c:/java/dev/projects/getjavajob/social-network-app/dao/src/test/resources/fillDB.sql")));

    }

    @After
    public void terminateTables() {
        try (Statement statement = connectionPool.getConnection().createStatement()) {
            statement.execute("DROP TABLE messages, account_in_group, soc_group, relationship, phone, account");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        boolean result = accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER);
        String expectedResultNewAccount = "Account{id=4, email='kolya1', password='123', firstName='Nikolay', " +
                "lastName='Malcev', middleName='Nikolaevich', birthday='1982-12-13', photoFileName='null', skype='dddd', " +
                "icq=1111, regDate='2018-06-13', role=USER, phones={}}";
        assertTrue(result);
        assertEquals(expectedResultNewAccount, accountDAO.get(4).toString());
    }

    @Test(expected = DaoNameException.class)
    public void createExceptionTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER);
        accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER);
    }

    @Test
    public void getTest() throws DaoException, DaoNameException {
        Account expected = new Account(4, "kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER, new HashMap<>());
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, null, "dddd", 1111,
                "2018-06-13", Role.USER);
        assertEquals(expected, accountDAO.get(4));
    }

    @Test
    public void getIdTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        assertEquals(1, accountDAO.getId("a@a.ru"));
    }

    @Test(expected = DaoNameException.class)
    public void getIdExceptionTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.getId("d@d.ru");
    }

    @Test
    public void loginAndGetIdTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        assertEquals(1, accountDAO.loginAndGetId("a@a.ru", "123"));
    }

    @Test(expected = DaoNameException.class)
    public void loginAndGetExceptionTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.loginAndGetId("a@a.ru", "");
    }

    @Test
    public void getRoleAdminTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        assertEquals(Role.ADMIN, accountDAO.getRole(1));
    }

    @Test
    public void getRoleUserTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        assertEquals(Role.USER, accountDAO.getRole(2));
    }


    @Test
    public void getAllTest() throws DaoException {
        Account account1 = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Urievich", "1988-07-22", null, null, "aaaaa", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        Account account2 = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
                null, "1990-01-01", null, null, "bbbbb", 0,
                "2018-06-13", Role.USER, new HashMap<>());
        Account account3 = new Account(3, "c@c.ru", "123", "Ivan", "Ivanov",
                "Ivanovich", "1970-05-29", null, null, "ccccc", 12345,
                "2018-06-13", Role.USER, new HashMap<>());
        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);
        expected.add(account3);
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        assertEquals(expected, accountDAO.getAll());
    }


    @Test
    public void updateAccountPasswordTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setPassword("456");
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "456", "Alexey", "Ershov",
                "Urievich", "1988-07-22", null, null, "aaaaa", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountFirstNameTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setFirstName("Ivan");
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "123", "Ivan", "Ershov",
                "Urievich", "1988-07-22", null, null, "aaaaa", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountLastNameTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setLastName("Bondarev");
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "123", "Alexey", "Bondarev",
                "Urievich", "1988-07-22", null, null, "aaaaa", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountMiddleNameTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setMiddleName("Vasilyevich");
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Vasilyevich", "1988-07-22", null, null, "aaaaa", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountBirthdayTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setBirthday("1990-01-01");
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Urievich", "1990-01-01", null, null, "aaaaa", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountSkypeTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setSkype("bbbbb");
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Urievich", "1988-07-22", null, null, "bbbbb", 1234567890,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountIcqTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setIcq(12345);
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "123", "Alexey", "Ershov",
                "Urievich", "1988-07-22", null, null, "aaaaa", 12345,
                "2018-06-08", Role.ADMIN, new HashMap<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateRoleTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        boolean result = accountDAO.updateRole(2, Role.ADMIN);
        Account expected = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
                null, "1990-01-01", null, null, "bbbbb", 0,
                "2018-06-13", Role.ADMIN, new HashMap<>());
        assertTrue(result);
        assertEquals(expected, accountDAO.get(2));
    }

    @Test(expected = DaoNameException.class)
    public void removeTest() throws DaoException, DaoNameException {
        AccountDAO accountDAO = new AccountDAO(connectionPool, phoneDAO);
        assertEquals(1, accountDAO.getId("a@a.ru"));
        assertTrue(accountDAO.remove(1));
        accountDAO.getId("a@a.ru");
    }
}