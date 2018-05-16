import exceptions.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AccountDAOTest {
    private Connection connection;

    @Before
    public void initDB() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("H2connect.properties"));
            String url = properties.getProperty("database.url");
            String user = properties.getProperty("database.user");
            String password = properties.getProperty("database.password");
            this.connection = DriverManager.getConnection(url, user, password);
            this.connection.setAutoCommit(false);
            ScriptRunnerUtil runner = new ScriptRunnerUtil(connection, true, true);
            runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/create-data-model.sql")));
            runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/fillDB.sql")));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    @After
    public void terminateTables() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("DROP TABLE accounts, account_info, groups, account_in_group, relationship");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void createTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connection);
        boolean result = accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-");
        String expectedResultNewAccount = "Account{id=5, username='kolya1', firstName='Nikolay', lastName='Malcev', " +
                "middleName='Nikolaevich', birthday='13.12.1982', phonePers='+79999999999', phoneWork='+79999999999', " +
                "addressPers='Lenina str. 145565', addressWork='Lenina 2', email='ddd@dd.ru', icq=12345, skype='asdf', extra='-'}";
        assertTrue(result);
        assertEquals(expectedResultNewAccount, accountDAO.get(5).toString());
    }

    @Test(expected = DaoException.class)
    public void createExceptionTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-");
        accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-");
    }

    @Test
    public void getTest() throws DaoException {
        Account expected = new Account(5, "kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-");
        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.create("kolya1", "123", "Nikolay", "Malcev",
                "Nikolaevich", "13.12.1982", "+79999999999", "+79999999999",
                "Lenina str. 145565", "Lenina 2", "ddd@dd.ru", 12345, "asdf", "-");
        assertEquals(expected, accountDAO.get(5));
    }

    @Test
    public void getAllTest() throws DaoException {
        Account account1 = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        Account account2 = new Account(2, "ivan1", "1234", "Ivan", "Ivanov", "Alexeyevich", "27.01.1993", "+79230000001", "+739121231212", "Moskow", "Moskow 2", "a2@a.ru", 1234500000, "bbbb", "extra information");
        Account account3 = new Account(3, "sergey1", "12345", "Sergey", "Nosov", "Ivanovych", "02.09.1987", "+79230000002", "+739121231212", "Sankt Petersburg", "Sankt Petersburg 2", "a3@a.ru", 1234500001, "ccccc", "extra information");
        Account account4 = new Account(4, "ivan2", "123456", "Ivan", "Menshov", "Maksimovich", "19.01.1981", "+79230000003", "+739121231212", "Novosibirsk 1", "Novosibirsk 2", "cc@c.ru", 1234500002, "dddd", "extra information");
        List<Account> expected = new ArrayList<>();
        expected.add(account1);
        expected.add(account2);
        expected.add(account3);
        expected.add(account4);
        AccountDAO accountDAO = new AccountDAO(connection);
        assertEquals(expected, accountDAO.getAll());
    }

    @Test
    public void getIdTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connection);
        assertEquals(2, accountDAO.getId("ivan1"));
    }

    @Test
    public void updatePasswordTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.updatePassword(1, "abc");
        assertEquals("abc", accountDAO.get(1).getPassword());
    }

    @Test
    public void updateAccountInfoFirstNameTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setFirstName("Ivan");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Ivan", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoLastNameTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setLastName("Bondarev");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Bondarev", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoMiddleNameTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setMiddleName("Vasilyevich");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Vasilyevich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoBirthdayTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setBirthday("01.01.1990");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "01.01.1990", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoPhonePersTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setPhonePers("+79230000001");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000001", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoPhoneWorkTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setPhoneWork("+79230000002");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+79230000002", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoAddressPersTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setAddressPers("Tver");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Tver",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoAddressWorkTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setAddressWork("Tver");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Tver", "a@a.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoEmailTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setEmail("b@b.ru");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "b@b.ru", 1234567890, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoIcqTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setIcq(12345);

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 12345, "aaaaa", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoSkypeTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setSkype("bbbbb");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "bbbbb", "extra information");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateAccountInfoExtraTest() throws DaoException {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setExtra("-------------");

        AccountDAO accountDAO = new AccountDAO(connection);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "alex1", "123", "Alexey", "Ershov", "Urievich",
                "22.07.1988", "+79230000000", "+739121231212", "Krasnoyarsk, Karla Marksa str.",
                "Krasnoyarsk, Svobodny pr.", "a@a.ru", 1234567890, "aaaaa", "-------------");
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void deleteTest() throws DaoException {
        AccountDAO accountDAO = new AccountDAO(connection);
        assertTrue(accountDAO.remove(1));
        assertEquals(3, accountDAO.getAll().size());
    }
}