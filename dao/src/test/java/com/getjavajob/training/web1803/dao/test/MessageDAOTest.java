package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.dao.MessageDAO;
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
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
@Transactional
public class MessageDAOTest {

    @Autowired
    private MessageDAO messageDAO;

    @Test
    public void getTest() {
        Message expected = new Message(1, MessageType.ACCOUNT, null, "Text account 1",
                "2018-06-17", 1);
        assertEquals(expected, messageDAO.get(1));
    }

    @Test
    public void createAccountMessageTest() {
        Message newMessage = new Message(1, MessageType.ACCOUNT, null, "Text 1",
                "2018-06-17", 1);
        messageDAO.create(newMessage);
        Message expected = new Message(1, MessageType.ACCOUNT, null, "Text 1",
                "2018-06-17", 1);
        assertEquals(expected, messageDAO.get(9));
    }

    @Test
    public void getAllByTypeAndAssignIdAccountTest() {
        Message message1 = new Message(1, MessageType.ACCOUNT, null, "Text account 1",
                "2018-06-17", 1);
        Message message2 = new Message(1, MessageType.ACCOUNT, null, "Text account 1-2",
                "2018-06-17", 1);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        assertEquals(messages, messageDAO.getAllByTypeAndAssignId(MessageType.ACCOUNT, 1));
    }

    @Test
    public void getAllByTypeAndAssignIdAccountWallTest() {
        Message message = new Message(2, MessageType.ACCOUNT_WALL, null, "Text account wall 2",
                "2018-06-17", 2);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        assertEquals(messages, messageDAO.getAllByTypeAndAssignId(MessageType.ACCOUNT_WALL, 2));
    }

    @Test
    public void getAllByTypeAndAssignIdGroupWallTest() {
        Message message1 = new Message(2, MessageType.GROUP_WALL, null, "Text group 2",
                "2018-06-17", 3);
        Message message2 = new Message(2, MessageType.GROUP_WALL, null, "Text group 2-2",
                "2018-06-17", 3);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        assertEquals(messages, messageDAO.getAllByTypeAndAssignId(MessageType.GROUP_WALL, 2));
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