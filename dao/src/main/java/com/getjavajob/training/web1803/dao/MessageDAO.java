package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
@Transactional
public class MessageDAO {
    private static final Logger logger = LoggerFactory.getLogger(MessageDAO.class);

    private EntityManager entityManager;

    @Autowired
    public MessageDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MessageDAO() {
    }

    @Transactional
    public int create(Message message) {
        logger.info("In create method");
        entityManager.persist(message);
        return 1;
    }

    public Message get(int id) {
        logger.info("In get method");
        return entityManager.find(Message.class, id);
    }

    public byte[] getPhoto(int id) {
        logger.info("In getPhoto method");
        return entityManager.find(Message.class, id).getPhoto();
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) {
        logger.info("In getAllByTypeAndAssignId method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> select = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("assignId"), assignId),
                        criteriaBuilder.equal(from.get("type"), type)));
        return entityManager.createQuery(select).getResultList();
    }

    public List<Integer> getAllAccountIdDialog(int currentId) {
        logger.info("In getAllAccountIdDialog method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("userCreatorId"), currentId)));
        List<Message> firstResult = entityManager.createQuery(firstSelect).getResultList();
        CriteriaQuery<Message> secondSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("assignId"), currentId)));
        List<Message> secondResult = entityManager.createQuery(secondSelect).getResultList();
        Set<Integer> result = new HashSet<>();
        for (Message message : firstResult) {
            result.add(message.getAssignId());
        }
        for (Message message : secondResult) {
            result.add(message.getUserCreatorId());
        }
        return new ArrayList<>(result);
    }

    //Map<IdMessage, IdAccountCreator>
    public Map<Integer, Integer> getAllByCurrentIdAssignId(int currentId, int assignId) {
        logger.info("In getAllByCurrentIdAssignId method");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("userCreatorId"), currentId),
                        criteriaBuilder.equal(from.get("assignId"), assignId)));
        List<Message> firstResult = entityManager.createQuery(firstSelect).getResultList();
        CriteriaQuery<Message> secondSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("userCreatorId"), assignId),
                        criteriaBuilder.equal(from.get("assignId"), currentId)));
        List<Message> secondResult = entityManager.createQuery(secondSelect).getResultList();
        Map<Integer, Integer> result = new HashMap<>();
        for (Message message : firstResult) {
            result.put(message.getId(), message.getUserCreatorId());
        }
        for (Message message : secondResult) {
            int id = message.getId();
            if (!result.containsKey(id)) {
                result.put(id, message.getUserCreatorId());
            }
        }
        return result;
    }

    @Transactional
    public boolean remove(int id) {
        logger.info("In remove method");
        Message message = entityManager.find(Message.class, id);
        entityManager.remove(message);
        entityManager.flush();
        return true;
    }
}