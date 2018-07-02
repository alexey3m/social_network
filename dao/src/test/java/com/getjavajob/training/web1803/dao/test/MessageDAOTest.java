package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.dao.MessageDAO;
import com.getjavajob.training.web1803.dao.Pool;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MessageDAOTest {
    private Pool connectionPool = new ConnectionPool();

    @Before
    public void initDB() throws IOException, SQLException, DaoException {
        ScriptRunnerUtil runner = new ScriptRunnerUtil(connectionPool.getConnection(), true, true);
        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/create-data-model.sql")));
        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/fillDB.sql")));
    }

    @After
    public void terminateTables() throws DaoException, SQLException {
        try (Statement statement = connectionPool.getConnection().createStatement()) {
            statement.execute("DROP TABLE message, account_in_group, soc_group, relationship, phone, account");
        }
    }

    @Test
    public void getTest() throws DaoException {
        MessageDAO messageDAO = new MessageDAO(connectionPool);
        Message expected = new Message(1, 1, MessageType.ACCOUNT, null, null, "Text account 1",
                "2018-06-17", 1);
        assertEquals(expected, messageDAO.get(1));
    }

    @Test
    public void createAccountMessageTest() throws DaoException {
        MessageDAO messageDAO = new MessageDAO(connectionPool);
        messageDAO.create(0, 1, MessageType.ACCOUNT, null, null, "Text 1",
                "2018-06-17", 1);
        Message expected = new Message(9, 1, MessageType.ACCOUNT, null, null, "Text 1",
                "2018-06-17", 1);
        assertEquals(expected, messageDAO.get(9));
    }

    @Test
    public void getAllByTypeAndAssignIdAccountTest() throws DaoException {
        MessageDAO messageDAO = new MessageDAO(connectionPool);
        Message message1 = new Message(1, 1, MessageType.ACCOUNT, null, null, "Text account 1",
                "2018-06-17", 1);
        Message message2 = new Message(2, 1, MessageType.ACCOUNT, null, null, "Text account 1-2",
                "2018-06-17", 1);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        assertEquals(messages, messageDAO.getAllByTypeAndAssignId(MessageType.ACCOUNT, 1));
    }

    @Test
    public void getAllByTypeAndAssignIdAccountWallTest() throws DaoException {
        MessageDAO messageDAO = new MessageDAO(connectionPool);
        Message message = new Message(5, 2, MessageType.ACCOUNT_WALL, null, null, "Text account wall 2",
                "2018-06-17", 2);
        List<Message> messages = new ArrayList<>();
        messages.add(message);
        assertEquals(messages, messageDAO.getAllByTypeAndAssignId(MessageType.ACCOUNT_WALL, 2));
    }

    @Test
    public void getAllByTypeAndAssignIdGroupWallTest() throws DaoException {
        MessageDAO messageDAO = new MessageDAO(connectionPool);
        Message message1 = new Message(7, 2, MessageType.GROUP_WALL, null, null, "Text group 2",
                "2018-06-17", 3);
        Message message2 = new Message(8, 2, MessageType.GROUP_WALL, null, null, "Text group 2-2",
                "2018-06-17", 3);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        assertEquals(messages, messageDAO.getAllByTypeAndAssignId(MessageType.GROUP_WALL, 2));
    }
}