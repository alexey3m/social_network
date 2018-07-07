package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.dao.MessageDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
public class MessageService {
    private MessageDAO messageDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public int create(int groupId, int accountId, MessageType type, InputStream photo, String photoFileName,
                      String text, String createDate, int userCreatorId) {
        return messageDAO.create(groupId, accountId, type, photo, photoFileName, text, createDate, userCreatorId);
    }

    public Message get(int id) {
        return messageDAO.get(id);
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) {
        return messageDAO.getAllByTypeAndAssignId(type, assignId);
    }

    public List<Integer> getAllAccountIdDialog(int currentId) {
        return messageDAO.getAllAccountIdDialog(currentId);
    }

    //Map<IdMessage, IdAccountCreator>
    public Map<Integer, Integer> getAllByCurrentIdAssignId(int currentId, int assignId) {
        return messageDAO.getAllByCurrentIdAssignId(currentId, assignId);
    }

    public boolean remove(int id) {
        return messageDAO.remove(id);
    }
}