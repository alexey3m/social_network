package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.enums.PhoneType;
import com.getjavajob.training.web1803.dao.PhoneDAO;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
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
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
public class PhoneDAOTest {

    @Autowired
    private PhoneDAO phoneDAO;

//    @Test
//    public void createTest() {
//        Map<String, PhoneType> expected = new HashMap<>();
//        expected.put("800", PhoneType.MOBILE);
//        boolean result = phoneDAO.create(2, "800", PhoneType.MOBILE);
//        assertTrue(result);
//        assertEquals(expected, phoneDAO.getAll(2));
//    }

    @Test
    public void getAllTest() {
        Map<String, PhoneType> expected = new HashMap<>();
        expected.put("900", PhoneType.MOBILE);
        expected.put("901", PhoneType.WORK);
        assertEquals(expected, phoneDAO.getAll(1));
    }

    @Test
    public void updateTest() {
        Map<String, PhoneType> newPhones = new HashMap<>();
        newPhones.put("800", PhoneType.WORK);
        newPhones.put("801", PhoneType.HOME);
        //boolean result = phoneDAO.update(1, newPhones);
        Map<String, PhoneType> expected = new HashMap<>();
        expected.put("800", PhoneType.WORK);
        expected.put("801", PhoneType.HOME);
        //assertTrue(result);
        assertEquals(expected, phoneDAO.getAll(1));
    }

    @Test
    public void removeTest() {
        Map<String, PhoneType> expected = new HashMap<>();
        phoneDAO.remove(1);
        assertEquals(expected, phoneDAO.getAll(1));
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

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }
    }
}