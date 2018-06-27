package com.getjavajob.training.web1803.service;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import com.getjavajob.training.web1803.dao.ConnectionPool;
import com.getjavajob.training.web1803.dao.MessageDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MessageService {
    private MessageDAO messageDAO;
    private ConnectionPool connectionPool;


    public MessageService() {
        connectionPool = ConnectionPool.getPool();
        messageDAO = MessageDAO.getInstance();
    }

    public int create(int groupId, int accountId, MessageType type, InputStream photo, String photoFileName,
                          String text, String createDate, int userCreatorId) {
        try {
            int id = messageDAO.create(groupId, accountId, type, photo, photoFileName, text, createDate, userCreatorId);
            connectionPool.commit();
            return id;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return 0;
        }
    }

    public Message get(int id) {
        try {
            return messageDAO.get(id);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) {
        try {
            return messageDAO.getAllByTypeAndAssignId(type, assignId);
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Integer> getAllAccountIdDialog(int currentId) {
        try {
            return messageDAO.getAllAccountIdDialog(currentId);
        } catch (DaoException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //Map<IdMessage, IdAccountCreator>
    public Map<Integer, Integer> getAllByCurrentIdAssignId(int currentId, int assignId) {
        try {
            return messageDAO.getAllByCurrentIdAssignId(currentId, assignId);
        } catch (DaoException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean remove(int id) {
        try {
            messageDAO.remove(id);
            connectionPool.commit();
            return true;
        } catch (DaoException e) {
            connectionPool.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public void closeService() {
        connectionPool.returnConnection();
    }
}