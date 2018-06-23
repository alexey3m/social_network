package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Message;
import com.getjavajob.training.web1803.common.MessageType;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDAO {
    private static final String INSERT_NEW_MESSAGE = "INSERT INTO message (assign_id, type, photo, photo_file_name, text, date_create, user_creator_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_MESSAGES = "SELECT * FROM message";
    private static final String SELECT_ACCOUNT_ID_WITH_DIALOG = "SELECT assign_id as id FROM message WHERE type = ? AND user_creator_id = ? " +
            "UNION " +
            "SELECT user_creator_id FROM message WHERE type = ? AND assign_id = ?";
    private static final String SELECT_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() AS id";
    private static final String SELECT_MESSAGE_ID_BY_CREATOR_ID = "SELECT message_id, user_creator_id FROM message WHERE type = ? AND user_creator_id = ? AND assign_id = ? " +
            "UNION " +
            "SELECT message_id, user_creator_id FROM message WHERE type = ? AND user_creator_id = ? AND assign_id = ?";
    private static final String SELECT_MESSAGE_BY_ID = SELECT_ALL_MESSAGES + " WHERE message_id = ?";
    private static final String SELECT_MESSAGE_BY_ASSIGN_ID_AND_TYPE = SELECT_ALL_MESSAGES + " WHERE assign_id = ? AND type = ?";
    private static final String REMOVE_MESSAGE = "DELETE FROM message WHERE message_id = ?";

    private Connection connection;
    private Pool connectionPool;

    public MessageDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
    }

    // Constructor for tests
    public MessageDAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public int create(int groupId, int accountId, MessageType type, InputStream photo, String photoFileName, String text, String createDate, int userCreatorId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_NEW_MESSAGE)) {
            preparedStatement.setInt(1, groupId != 0 ? groupId : accountId);
            preparedStatement.setInt(2, type.getStatus());
            preparedStatement.setBlob(3, photo);
            preparedStatement.setString(4, photoFileName);
            preparedStatement.setString(5, text);
            preparedStatement.setString(6, createDate);
            preparedStatement.setInt(7, userCreatorId);
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = this.connection.createStatement().executeQuery(SELECT_LAST_INSERT_ID)) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }
            }
            return 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Message get(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_MESSAGE_BY_ID)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next() ) {
                    return createMessageFromResult(resultSet);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Message> getAllByTypeAndAssignId(MessageType type, int assignId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement =  this.connection.prepareStatement(SELECT_MESSAGE_BY_ASSIGN_ID_AND_TYPE)) {
            preparedStatement.setInt(1, assignId);
            preparedStatement.setInt(2, type.getStatus());
            List<Message> messages = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    messages.add(createMessageFromResult(resultSet));
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Integer> getAllAccountIdDialog(int currentId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement =  this.connection.prepareStatement(SELECT_ACCOUNT_ID_WITH_DIALOG)) {
            preparedStatement.setInt(1, MessageType.ACCOUNT.getStatus());
            preparedStatement.setInt(2, currentId);
            preparedStatement.setInt(3, MessageType.ACCOUNT.getStatus());
            preparedStatement.setInt(4, currentId);
            List<Integer> accounts = new ArrayList<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    accounts.add(resultSet.getInt("id"));
                }
            }
            return accounts;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Map<Integer, Integer> getAllByCurrentIdAssignId(int currentId, int assignId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement =  this.connection.prepareStatement(SELECT_MESSAGE_ID_BY_CREATOR_ID)) {
            preparedStatement.setInt(1, MessageType.ACCOUNT.getStatus());
            preparedStatement.setInt(2, currentId);
            preparedStatement.setInt(3, assignId);
            preparedStatement.setInt(4, MessageType.ACCOUNT.getStatus());
            preparedStatement.setInt(5, assignId);
            preparedStatement.setInt(6, currentId);
            Map<Integer, Integer> messages = new HashMap<>();
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    messages.put(resultSet.getInt("message_id"), resultSet.getInt("user_creator_id"));
                }
            }
            return messages;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean remove(int id) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement1 = this.connection.prepareStatement(REMOVE_MESSAGE)) {
            preparedStatement1.setInt(1, id);
            preparedStatement1.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
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