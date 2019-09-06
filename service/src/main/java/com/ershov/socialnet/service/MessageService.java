package com.ershov.socialnet.service;

import com.ershov.socialnet.common.Message;
import com.ershov.socialnet.common.enums.MessageType;
import com.ershov.socialnet.dao.repository.MessageRepository;
import com.ershov.socialnet.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class MessageService {
    private MessageRepository repository;

    @Autowired
    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Message create(Message message) {
        return repository.saveAndFlush(message);
    }

    public Message get(int id) {
        return repository.findById(id).orElseThrow(() -> new ServiceException("")); //TODO: 13.08.2019 Add some text to ServiceException
    }

    public byte[] getPhoto(int id) {
        return repository.findById(id).orElseThrow(() -> new ServiceException("")).getPhoto();
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) {
        return repository.getAllByTypeAndAssignId(type, assignId);
    }

    public List<Integer> getAllAccountIdDialog(int currentId) {
        List<Message> assignIds = repository.getAllByTypeAndAssignId(MessageType.ACCOUNT, currentId);
        List<Message> creatorIds = repository.getAllByTypeAndUserCreatorId(MessageType.ACCOUNT, currentId);

        List<Integer> result = assignIds.stream().map(Message::getAssignId).collect(Collectors.toList());
        result.addAll(creatorIds.stream().map(Message::getUserCreatorId).collect(Collectors.toList()));
        return result;
    }

    //Map<IdMessage, IdAccountCreator>
    public Map<Integer, Integer> getAllByCurrentIdAssignId(int currentId, int assignId) {
        List<Message> currentIds = repository.getAllByTypeAndUserCreatorIdAndAssignId(MessageType.ACCOUNT, currentId, assignId);
        List<Message> assignIds = repository.getAllByTypeAndUserCreatorIdAndAssignId(MessageType.ACCOUNT, assignId, currentId);

        Map<Integer, Integer> result = new TreeMap<>();
        currentIds.forEach(message -> result.put(message.getId(), message.getUserCreatorId()));
        assignIds.forEach(message -> result.put(message.getId(), message.getUserCreatorId()));
        return result;
    }

    @Transactional
    public void remove(int id) {
        repository.deleteById(id);
        repository.flush();
    }
}