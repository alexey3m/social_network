package com.getjavajob.training.web1803.service.test;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.dao.MessageDAO;
import com.getjavajob.training.web1803.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageDAO messageDAO;

    @InjectMocks
    private MessageService messageService;

    @Test
    public void createTest() {

        when(messageDAO.create(new Message(1, MessageType.ACCOUNT, null, "Text account 1", "2018-06-17", 1))).thenReturn(1);
        assertEquals(1, messageService.create(new Message(1, MessageType.ACCOUNT, null, "Text account 1", "2018-06-17", 1)));
    }

    @Test
    public void getTest() {
        Message message = new Message(1, MessageType.ACCOUNT, null, "Text account 1",
                "2018-06-17", 1);
        when(messageDAO.get(1)).thenReturn(message);
        assertEquals(message, messageService.get(1));
    }

    @Test
    public void getAllByTypeAndAssignIdTest() {
        Message message1 = new Message(1, MessageType.ACCOUNT, null, "Text account 1",
                "2018-06-17", 1);
        Message message2 = new Message(1, MessageType.ACCOUNT, null, "Text account 1-2",
                "2018-06-17", 1);
        List<Message> messages = new ArrayList<>();
        messages.add(message1);
        messages.add(message2);
        when(messageDAO.getAllByTypeAndAssignId(MessageType.ACCOUNT, 1)).thenReturn(messages);
        assertEquals(messages, messageService.getAllByTypeAndAssignId(MessageType.ACCOUNT, 1));
    }

    @Test
    public void getAllAccountIdDialogTest() {
        List<Integer> accountsId = new ArrayList<>();
        accountsId.add(1);
        accountsId.add(2);
        accountsId.add(3);
        when(messageDAO.getAllAccountIdDialog(1)).thenReturn(accountsId);
        assertEquals(accountsId, messageService.getAllAccountIdDialog(1));
    }

    @Test
    public void getAllByCurrentIdAssignIdTest() {
        Map<Integer, Integer> messMap = new HashMap<>();
        messMap.put(3, 1);
        when(messageDAO.getAllByCurrentIdAssignId(1, 2)).thenReturn(messMap);
        assertEquals(messMap, messageService.getAllByCurrentIdAssignId(1, 2));
    }

    @Test
    public void removeTest() {
        when(messageDAO.remove(1)).thenReturn(true);
        assertTrue(messageDAO.remove(1));
    }
}