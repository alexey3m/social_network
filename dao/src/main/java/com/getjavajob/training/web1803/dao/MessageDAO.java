package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.enums.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MessageDAO {
    private static final String INSERT_NEW_MESSAGE = "INSERT INTO message (assign_id, type, photo, photo_file_name, text, " +
            "date_create, user_creator_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_MESSAGES = "SELECT * FROM message";
    private static final String SELECT_ACCOUNT_ID_WITH_DIALOG = "SELECT assign_id as id FROM message WHERE type = ? AND user_creator_id = ? " +
            "UNION " +
            "SELECT user_creator_id FROM message WHERE type = ? AND assign_id = ?";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS id";
    private static final String SELECT_MESSAGE_ID_BY_CREATOR_ID = "SELECT message_id, user_creator_id, date_create FROM message " +
            "WHERE type = ? AND user_creator_id = ? AND assign_id = ? " +
            "UNION " +
            "SELECT message_id, user_creator_id, date_create FROM message WHERE type = ? AND user_creator_id = ? AND assign_id = ? " +
            "ORDER BY date_create, message_id";
    private static final String SELECT_MESSAGE_BY_ID = SELECT_ALL_MESSAGES + " WHERE message_id = ?";
    private static final String SELECT_MESSAGE_BY_ASSIGN_ID_AND_TYPE = SELECT_ALL_MESSAGES + " WHERE assign_id = ? AND type = ?";
    private static final String REMOVE_MESSAGE = "DELETE FROM message WHERE message_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public MessageDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int create(int groupId, int accountId, MessageType type, InputStream photo, String photoFileName, String text,
                      String createDate, int userCreatorId) {
        int assignId = groupId != 0 ? groupId : accountId;
        int result = this.jdbcTemplate.update(INSERT_NEW_MESSAGE, assignId, type.getStatus(), photo, photoFileName, text,
                createDate, userCreatorId);
        return result == 0 ? 0 : this.jdbcTemplate.queryForObject(SELECT_LAST_INSERT_ID, (rs, rowNum) -> rs.getInt("id"));
    }

    public Message get(int id) {
        return this.jdbcTemplate.queryForObject(SELECT_MESSAGE_BY_ID, new Object[]{id},
                (rs, rowNum) -> createMessageFromResult(rs));
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) {
        return this.jdbcTemplate.query(SELECT_MESSAGE_BY_ASSIGN_ID_AND_TYPE, new Object[]{assignId, type.getStatus()},
                (rs, rowNum) -> createMessageFromResult(rs));
    }

    public List<Integer> getAllAccountIdDialog(int currentId) {
        return this.jdbcTemplate.query(SELECT_ACCOUNT_ID_WITH_DIALOG, new Object[]{MessageType.ACCOUNT.getStatus(),
                currentId, MessageType.ACCOUNT.getStatus(), currentId}, (rs, rowNum) -> rs.getInt("id"));
    }

    public Map<Integer, Integer> getAllByCurrentIdAssignId(int currentId, int assignId) {
        return this.jdbcTemplate.query(SELECT_MESSAGE_ID_BY_CREATOR_ID, new Object[]{MessageType.ACCOUNT.getStatus(),
                currentId, assignId, MessageType.ACCOUNT.getStatus(), assignId, currentId}, rs -> {
            Map<Integer, Integer> messages = new HashMap<>();
            while (rs.next()) {
                messages.put(rs.getInt("message_id"), rs.getInt("user_creator_id"));
            }
            return messages;
        });
    }

    @Transactional
    public boolean remove(int id) {
        int result = this.jdbcTemplate.update(REMOVE_MESSAGE, id);
        return result != 0;
    }

    private Message createMessageFromResult(ResultSet resultSet) throws SQLException {
        Message message = new Message();
        message.setId(resultSet.getInt("message_id"));
        message.setAssignId(resultSet.getInt("assign_id"));
        message.setType(MessageType.values()[resultSet.getInt("type")]);
        message.setPhoto(resultSet.getBytes("photo"));
        message.setPhotoFileName(resultSet.getString("photo_file_name"));
        message.setText(resultSet.getString("text"));
        message.setCreateDate(resultSet.getString("date_create"));
        message.setUserCreatorId(resultSet.getInt("user_creator_id"));
        return message;
    }
}