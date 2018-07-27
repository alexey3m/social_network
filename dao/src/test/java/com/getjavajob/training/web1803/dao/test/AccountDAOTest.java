package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.Account;
import com.getjavajob.training.web1803.common.enums.Role;
import com.getjavajob.training.web1803.dao.AccountDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
@Transactional
public class AccountDAOTest {

    @Autowired
    private AccountDAO accountDAO;

    @Test
    public void createTest() throws DaoNameException {
        Account account = new Account(0, "kolya1@mail", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, "dddd", 1111,
                "2018-06-13", Role.USER, null);
        boolean result = accountDAO.create(account);
        String expectedResultNewAccount = "Account{id=4, email='kolya1@mail', password='123', firstName='Nikolay', " +
                "lastName='Malcev', middleName='Nikolaevich', birthday='1982-12-13', photo=null, skype='dddd', " +
                "icq=1111, regDate='2018-06-13', role=USER, phones=null}";
        assertTrue(result);
        assertEquals(expectedResultNewAccount, accountDAO.get(4).toString());
    }

    @Test(expected = DaoNameException.class)
    public void createExceptionTest() throws DaoNameException {
        Account account = new Account(0, "kolya1@mail", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, "dddd", 1111,
                "2018-06-13", Role.USER, null);
        accountDAO.create(account);
        accountDAO.create(account);
    }

    @Test
    public void getTest() throws DaoNameException {
        Account expected = new Account(4, "kolya1@mail", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, "dddd", 1111,
                "2018-06-13", Role.USER, null);
        Account account = new Account(0, "kolya1@mail", "123", "Nikolay", "Malcev",
                "Nikolaevich", "1982-12-13", null, "dddd", 1111,
                "2018-06-13", Role.USER, null);
        accountDAO.create(account);
        assertEquals(expected, accountDAO.get(4));
    }

    @Test
    public void loginAndGetIdTest() throws DaoNameException {
        assertEquals(1, accountDAO.loginAndGetId("a@a.ru", "123"));
    }

    @Test(expected = DaoNameException.class)
    public void loginAndGetExceptionTest() throws DaoNameException {
        accountDAO.loginAndGetId("a@a.ru", "");
    }

    @Test
    public void getRoleAdminTest() {
        assertEquals(Role.ADMIN, accountDAO.getRole(1));
    }

    @Test
    public void getRoleUserTest() {
        assertEquals(Role.USER, accountDAO.getRole(2));
    }

    @Test
    public void updateAccountTest() {
        Account accountUpdate = new Account();
        accountUpdate.setId(1);
        accountUpdate.setEmail("a@a.ru");
        accountUpdate.setPassword("456");
        accountUpdate.setFirstName("Ivan");
        accountUpdate.setLastName("Ivanov");
        accountUpdate.setMiddleName("Ivanovych");
        accountUpdate.setBirthday("1988-07-23");
        accountUpdate.setSkype("bbbbb");
        accountUpdate.setIcq(123456);
        accountUpdate.setRegDate("2018-06-08");
        accountUpdate.setRole(Role.ADMIN);
        accountDAO.update(accountUpdate);
        Account expected = new Account(1, "a@a.ru", "456", "Ivan", "Ivanov",
                "Ivanovych", "1988-07-23", null, "bbbbb", 123456,
                "2018-06-08", Role.ADMIN, new ArrayList<>());
        assertEquals(expected, accountDAO.get(1));
    }

    @Test
    public void updateRoleTest() {
        boolean result = accountDAO.updateRole(2, Role.ADMIN);
        Account expected = new Account(2, "b@b.ru", "123", "Sergey", "Semenov",
                "", "1990-01-01", null, "bbbbb", 0,
                "2018-06-13", Role.ADMIN, new ArrayList<>());
        assertTrue(result);
        assertEquals(expected, accountDAO.get(2));
    }

    @Test
    public void removeTest() {
        assertTrue(accountDAO.remove(1));
    }

    @Configuration
    @PropertySource("classpath:H2Connect.properties")
    static class Config {

        @Autowired
        private Environment env;

        @Bean
        public DataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(env.getProperty("database.url"));
            dataSource.setUsername(env.getProperty("database.user"));
            dataSource.setPassword(env.getProperty("database.password"));
            return dataSource;
        }
    }
}