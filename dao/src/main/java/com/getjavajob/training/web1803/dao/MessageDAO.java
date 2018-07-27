package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Repository
@Transactional
public class MessageDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public MessageDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public MessageDAO() {
    }

    @Transactional
    public int create(Message message) {
        sessionFactory.getCurrentSession().persist(message);
        return 1;
    }

    public Message get(int id) {
        return sessionFactory.getCurrentSession().get(Message.class, id);
    }

    public byte[] getPhoto(int id) {
        return sessionFactory.getCurrentSession().get(Message.class, id).getPhoto();
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> select = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("assignId"), assignId),
                        criteriaBuilder.equal(from.get("type"), type)));
        return session.createQuery(select).getResultList();
    }

    public List<Integer> getAllAccountIdDialog(int currentId) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("userCreatorId"), currentId)));
        List<Message> firstResult = session.createQuery(firstSelect).getResultList();
        CriteriaQuery<Message> secondSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("assignId"), currentId)));
        List<Message> secondResult = session.createQuery(secondSelect).getResultList();
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

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);

        CriteriaQuery<Message> firstSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("userCreatorId"), currentId),
                        criteriaBuilder.equal(from.get("assignId"), assignId)));
        List<Message> firstResult = session.createQuery(firstSelect).getResultList();

        CriteriaQuery<Message> secondSelect = criteriaQuery.select(from).where(
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("type"), MessageType.ACCOUNT),
                        criteriaBuilder.equal(from.get("userCreatorId"), assignId),
                        criteriaBuilder.equal(from.get("assignId"), currentId)));
        List<Message> secondResult = session.createQuery(secondSelect).getResultList();

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

//        return this.jdbcTemplate.query(SELECT_MESSAGE_ID_BY_CREATOR_ID, new Object[]{MessageType.ACCOUNT,
//                currentId, assignId, MessageType.ACCOUNT, assignId, currentId}, rs -> {
//            Map<Integer, Integer> messages = new HashMap<>();
//            while (rs.next()) {
//                messages.put(rs.getInt("message_id"), rs.getInt("user_creator_id"));
//            }
//            return messages;
//        });
    }

    @Transactional
    public boolean remove(int id) {
        Session session = sessionFactory.getCurrentSession();
        Message message = session.get(Message.class, id);
        session.remove(message);
        return true;
    }

//    private Message createMessageFromResult(ResultSet resultSet) throws SQLException {
//        Message message = new Message();
//        message.setId(resultSet.getInt("message_id"));
//        message.setAssignId(resultSet.getInt("assign_id"));
//        message.setType(MessageType.values()[resultSet.getInt("type")]);
//        message.setPhoto(resultSet.getBytes("photo"));
//        message.setPhotoFileName(resultSet.getString("photo_file_name"));
//        message.setText(resultSet.getString("text"));
//        message.setDateCreate(resultSet.getString("date_create"));
//        message.setUserCreatorId(resultSet.getInt("user_creator_id"));
//        return message;
//    }
}