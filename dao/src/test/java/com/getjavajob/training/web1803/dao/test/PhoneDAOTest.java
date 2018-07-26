package com.getjavajob.training.web1803.dao.test;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
public class PhoneDAOTest {
//
//    @Autowired
//    private PhoneDAO phoneDAO;
//
//    @Test
//    public void createTest() {
//        List<Phone> phones = new ArrayList<>();
//        phones.add(new Phone("800", PhoneType.MOBILE));
//        Account account = new Account(2, null, null, null, null, null,
//                null, null, null, 0, null, null, phones);
//        List<Phone> expected = new ArrayList<>();
//        expected.add(new Phone("800", PhoneType.MOBILE));
//        boolean result = phoneDAO.create(account);
//        assertTrue(result);
//        assertEquals(expected, phoneDAO.getAll(2));
//    }
//
//    @Test
//    public void getAllTest() {
//        List<Phone> expected = new ArrayList<>();
//        expected.add(new Phone("900", PhoneType.MOBILE));
//        expected.add(new Phone("901", PhoneType.WORK));
//        assertEquals(expected, phoneDAO.getAll(1));
//    }
//
//    @Test
//    public void updateTest() {
//        List<Phone> newPhones = new ArrayList<>();
//        newPhones.add(new Phone("800", PhoneType.MOBILE));
//        newPhones.add(new Phone("801", PhoneType.MOBILE));
//        Account account = new Account(1, null, null, null, null, null,
//                null, null, null, 0, null, null, newPhones);
//        boolean result = phoneDAO.update(account);
//        List<Phone> expected = new ArrayList<>();
//        expected.add(new Phone("800", PhoneType.MOBILE));
//        expected.add(new Phone("801", PhoneType.MOBILE));
//        assertTrue(result);
//        assertEquals(expected, phoneDAO.getAll(1));
//    }
//
//    @Test
//    public void removeTest() {
//        List<Phone> expected = new ArrayList<>();
//        phoneDAO.remove(1);
//        assertEquals(expected, phoneDAO.getAll(1));
//    }

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

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }
}