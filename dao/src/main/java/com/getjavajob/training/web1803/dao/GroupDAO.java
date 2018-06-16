package com.getjavajob.training.web1803.dao;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.GroupRole;
import com.getjavajob.training.web1803.common.GroupStatus;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private static final String SELECT_ALL_GROUPS = "SELECT * FROM soc_group";
    private static final String SELECT_GROUP_BY_NAME = SELECT_ALL_GROUPS + " WHERE name = ?";
    private static final String INSERT_GROUP = "INSERT INTO soc_group (name, photo, photo_file_name, create_date, " +
            "info, user_creator_id) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ACCOUNT_IN_GROUP = "INSERT INTO account_in_group (group_id, user_member_id, role, status) VALUES (?, ?, ?, ?)";
    private static final String SELECT_GROUPS_ID_BY_NAME = "SELECT group_id FROM soc_group WHERE name = ?";
    private static final String SELECT_GROUPS_ID_BY_USER_MEMBER_ID = "SELECT * FROM soc_group WHERE group_id IN " +
            "(SELECT group_id FROM account_in_group WHERE user_member_id = ? AND status = 2)";
    private static final String SELECT_ROLE_MEMBER = "SELECT role FROM account_in_group WHERE group_id = ? AND user_member_id = ?";
    private static final String SELECT_STATUS_MEMBER = "SELECT status FROM account_in_group WHERE group_id = ? AND user_member_id = ?";
    private static final String COLUMN_GROUP_ID = "group_id";
    private static final String SELECT_GROUP_BY_ID = SELECT_ALL_GROUPS + " WHERE group_id = ?";
    private static final String SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP = "SELECT * FROM account_in_group WHERE group_id = ?";
    private static final String UPDATE_GROUP_SET_NAME = "UPDATE soc_group SET name = ? WHERE group_id = ?";
    private static final String UPDATE_GROUP_SET_PHOTO = "UPDATE soc_group SET photo = ? WHERE group_id = ?";
    private static final String UPDATE_GROUP_SET_PHOTO_FILE_NAME = "UPDATE soc_group SET photo_file_name = ? WHERE group_id = ?";
    private static final String UPDATE_GROUP_SET_INFO = "UPDATE soc_group SET info = ? WHERE group_id = ?";
    private static final String UPDATE_STATUS_MEMBER = "UPDATE account_in_group SET status = ? WHERE group_id = ? AND user_member_id = ?";
    private static final String UPDATE_ROLE_MEMBER = "UPDATE account_in_group SET role = ? WHERE group_id = ? AND user_member_id = ?";
    private static final String REMOVE_ACCOUNT_IN_GROUP = "DELETE FROM account_in_group WHERE user_member_id = ? AND group_id = ?";
    private static final String REMOVE_ALL_ACCOUNTS_IN_GROUP = "DELETE FROM account_in_group WHERE group_id = ?";
    private static final String REMOVE_GROUP = "DELETE FROM soc_group WHERE group_id = ?";

    private Connection connection;
    private Pool connectionPool;

    public GroupDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
    }

    // Constructor for tests
    public GroupDAO(Pool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public boolean create(String name, InputStream photo, String photoFileName, String createDate, String info, int userCreatorId) throws DaoNameException, DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_GROUP_BY_NAME)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    boolean result = insertGroup(name, photo, photoFileName, createDate, info, userCreatorId);
//                    this.connection.commit();
                    return result;
                } else {
                    throw new DaoNameException("Group name \"" + name + "\" is already used.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public Group get(int groupId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatementGroup = this.connection.prepareStatement(SELECT_GROUP_BY_ID);
             PreparedStatement preparedStatementMembers = this.connection.prepareStatement(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP)) {
            preparedStatementGroup.setInt(1, groupId);
            preparedStatementMembers.setInt(1, groupId);
            try (ResultSet resultSetGroup = preparedStatementGroup.executeQuery();
                 ResultSet resultSetMembers = preparedStatementMembers.executeQuery()) {
                if (resultSetGroup.next()) {
                    return createGroupFromResult(resultSetGroup, resultSetMembers);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Group> getAll() throws DaoException {
        connection = connectionPool.getConnection();
        try (ResultSet resultSetGroups = this.connection.createStatement().executeQuery(SELECT_ALL_GROUPS)) {
            List<Group> groups = new ArrayList<>();
            while (resultSetGroups.next()) {
                try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP)) {
                    preparedStatement.setInt(1, resultSetGroups.getInt(COLUMN_GROUP_ID));
                    try (ResultSet resultSetMembers = preparedStatement.executeQuery()) {
                        groups.add(createGroupFromResult(resultSetGroups, resultSetMembers));
                    }
                }
            }
            return groups;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public List<Group> getAllById(int userId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatementGroup = this.connection.prepareStatement(SELECT_GROUPS_ID_BY_USER_MEMBER_ID);
             PreparedStatement preparedStatementMembers = this.connection.prepareStatement(SELECT_USER_MEMBER_ID_FROM_ACCOUNT_IN_GROUP)) {
            preparedStatementGroup.setInt(1, userId);
            List<Group> groups = new ArrayList<>();
            try (ResultSet resultSetGroup = preparedStatementGroup.executeQuery()) {
                while (resultSetGroup.next()) {
                    preparedStatementMembers.setInt(1, resultSetGroup.getInt(COLUMN_GROUP_ID));
                    try (ResultSet resultSetMembers = preparedStatementMembers.executeQuery()) {
                        groups.add(createGroupFromResult(resultSetGroup, resultSetMembers));
                    }
                }
            }
            return groups;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public int getId(String name) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatementGroupID = this.connection.prepareStatement(SELECT_GROUPS_ID_BY_NAME)) {
            preparedStatementGroupID.setString(1, name);
            try (ResultSet resultSet = preparedStatementGroupID.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(COLUMN_GROUP_ID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return -1;
    }

    public GroupRole getRoleMemberInGroup(int groupId, int memberId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatementGroupID = this.connection.prepareStatement(SELECT_ROLE_MEMBER)) {
            preparedStatementGroupID.setInt(1, groupId);
            preparedStatementGroupID.setInt(2, memberId);
            try (ResultSet resultSet = preparedStatementGroupID.executeQuery()) {
                return resultSet.next() ? GroupRole.values()[resultSet.getInt("role")] : GroupRole.UNKNOWN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    public GroupStatus getStatusMemberInGroup(int groupId, int memberId) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatementGroupID = this.connection.prepareStatement(SELECT_STATUS_MEMBER)) {
            preparedStatementGroupID.setInt(1, groupId);
            preparedStatementGroupID.setInt(2, memberId);
            try (ResultSet resultSet = preparedStatementGroupID.executeQuery()) {
                return resultSet.next() ? GroupStatus.values()[resultSet.getInt("status")] : GroupStatus.UNKNOWN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionPool.returnConnection(connection);
        }
        return null;
    }

    public boolean update(Group group) throws DaoException {
        connection = connectionPool.getConnection();
        int id = group.getId();
        executePrepStatementUpdateString(id, group.getName(), this.connection, UPDATE_GROUP_SET_NAME);
        byte[] photo = group.getPhoto();
        if (photo != null) {
            try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_GROUP_SET_PHOTO)) {
                preparedStatement.setBytes(1, photo);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        executePrepStatementUpdateString(id, group.getPhotoFileName(), this.connection, UPDATE_GROUP_SET_PHOTO_FILE_NAME);
        executePrepStatementUpdateString(id, group.getInfo(), this.connection, UPDATE_GROUP_SET_INFO);
        try {
//            this.connection.commit();
            return true;
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean addPendingMemberToGroup(int idGroup, int idNewMember) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_ACCOUNT_IN_GROUP)) {
            preparedStatement.setInt(1, idGroup);
            preparedStatement.setInt(2, idNewMember);
            preparedStatement.setInt(3, GroupRole.USER.getStatus());
            preparedStatement.setInt(4, GroupStatus.PENDING.getStatus());
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean setStatusMemberInGroup(int idGroup, int member, GroupStatus status) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_STATUS_MEMBER)) {
            preparedStatement.setInt(1, status.getStatus());
            preparedStatement.setInt(2, idGroup);
            preparedStatement.setInt(3, member);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean setRoleMemberInGroup(int idGroup, int member, GroupRole role) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE_ROLE_MEMBER)) {
            preparedStatement.setInt(1, role.getStatus());
            preparedStatement.setInt(2, idGroup);
            preparedStatement.setInt(3, member);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(REMOVE_ACCOUNT_IN_GROUP)) {
            preparedStatement.setInt(1, idMemberToDelete);
            preparedStatement.setInt(2, idGroup);
            preparedStatement.executeUpdate();
//            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    public boolean remove(int idGroup) throws DaoException {
        connection = connectionPool.getConnection();
        try (PreparedStatement preparedStatementMembers = this.connection.prepareStatement(REMOVE_ALL_ACCOUNTS_IN_GROUP)) {
            preparedStatementMembers.setInt(1, idGroup);
            preparedStatementMembers.executeUpdate();
            try (PreparedStatement preparedStatementGroup = this.connection.prepareStatement(REMOVE_GROUP)) {
                preparedStatementGroup.setInt(1, idGroup);
                preparedStatementGroup.executeUpdate();
//                this.connection.commit();
                return true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.returnConnection(connection);
        }
    }

    private boolean insertGroup(String name, InputStream photo, String photoFileName, String createDate, String info, int userCreatorId) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_GROUP)) {
            preparedStatement.setString(1, name);
            preparedStatement.setBlob(2, photo);
            preparedStatement.setString(3, photoFileName);
            preparedStatement.setString(4, createDate);
            preparedStatement.setString(5, info);
            preparedStatement.setInt(6, userCreatorId);
            preparedStatement.executeUpdate();
            int groupId = getId(name);
            try (PreparedStatement preparedStatementInsertAccountInGroup = this.connection.prepareStatement(INSERT_ACCOUNT_IN_GROUP)) {
                preparedStatementInsertAccountInGroup.setInt(1, groupId);
                preparedStatementInsertAccountInGroup.setInt(2, userCreatorId);
                preparedStatementInsertAccountInGroup.setInt(3, GroupRole.ADMIN.getStatus());
                preparedStatementInsertAccountInGroup.setInt(4, GroupStatus.ACCEPTED.getStatus());
                preparedStatementInsertAccountInGroup.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Group createGroupFromResult(ResultSet resultSetGroup, ResultSet resultSetMembers) throws SQLException {
        Group group = new Group();
        group.setId(resultSetGroup.getInt(COLUMN_GROUP_ID));
        group.setName(resultSetGroup.getString("name"));
        group.setPhoto(resultSetGroup.getBytes("photo"));
        group.setPhotoFileName(resultSetGroup.getString("photo_file_name"));
        group.setCreateDate(resultSetGroup.getString("create_date"));
        group.setInfo(resultSetGroup.getString("info"));
        group.setUserCreatorId(resultSetGroup.getInt("user_creator_id"));
        List<Integer> acceptedMembersId = new ArrayList<>();
        List<Integer> pendingMembersId = new ArrayList<>();
        List<Integer> adminsId = new ArrayList<>();
        while (resultSetMembers.next()) {
            int memberId = resultSetMembers.getInt("user_member_id");
            int role = resultSetMembers.getInt("role");
            int status = resultSetMembers.getInt("status");
            if (GroupStatus.values()[status] == GroupStatus.ACCEPTED) {
                acceptedMembersId.add(memberId);
            } else if (GroupStatus.values()[status] == GroupStatus.PENDING) {
                pendingMembersId.add(memberId);
            }
            if (GroupRole.values()[role] == GroupRole.ADMIN) {
                adminsId.add(memberId);
            }
        }
        group.setAcceptedMembersId(acceptedMembersId);
        group.setPendingMembersId(pendingMembersId);
        group.setAdminsId(adminsId);
        return group;
    }

    private static void executePrepStatementUpdateString(int id, String field, Connection connection, String query) throws DaoException {
        if (field != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, field);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
}