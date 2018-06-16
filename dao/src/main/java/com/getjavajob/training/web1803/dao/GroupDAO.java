import exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    private static final String SELECT_ALL_SOC_GROUPS = "SELECT * FROM soc_groups";
    private static final String SELECT_GROUP_BY_NAME = SELECT_ALL_SOC_GROUPS + " WHERE name = ?";
    private static final String SELECT_GROUP_BY_ID = SELECT_ALL_SOC_GROUPS + " WHERE group_id = ?";
    private static final String SELECT_ACCOUNT_ID_FROM_ACCOUNT_IN_GROUP = "SELECT account_id FROM account_in_group WHERE group_id = ?";
    private static final String SELECT_ACCOUNT_ID_ADMIN_FROM_SOC_GROUPS = "SELECT account_id_admin FROM soc_groups WHERE group_id = ?";
    private static final String SELECT_GROUP_ID_FROM_SOC_GROUPS = "SELECT group_id FROM soc_groups WHERE name = ?";
    private static final String UPDATE_SOC_GROUPS_SET_NAME = "UPDATE soc_groups SET name = ? WHERE group_id = ?";
    private static final String UPDATE_SOC_GROUPS_SET_INFO = "UPDATE soc_groups SET info = ? WHERE group_id = ?";
    private static final String UPDATE_SOC_GROUPS_SET_ACCOUNT_ID_ADMIN = "UPDATE soc_groups SET account_id_admin = ? WHERE group_id = ?";
    private static final String UPDATE_ACCOUNT_IN_GROUP_SET_ACCOUNT_ID = "UPDATE account_in_group SET account_id = ? WHERE account_id = ? AND group_id = ?";
    private static final String INSERT_INTO_ACCOUNT_IN_GROUP = "INSERT INTO account_in_group (account_id, group_id) VALUES (?, ?)";
    private static final String DELETE_FROM_ACCOUNT_IN_GROUP = "DELETE FROM account_in_group WHERE account_id = ? AND group_id = ?";
    private static final String INSERT_INTO_SOC_GROUPS = "INSERT INTO soc_groups (name, info, account_id_admin) VALUES(?, ?, ?)";
    private static final String DELETE_FROM_SOC_GROUPS = "DELETE FROM soc_groups WHERE group_id = ?";
    private static final String COLUMN_GROUP_ID = "group_id";

    private Connection connection;
    private ConnectionPool connectionPool;

    public GroupDAO() throws DaoException {
        connectionPool = ConnectionPool.getPool();
        connection = connectionPool.getConnection();
    }

    // Constructor for tests
    public GroupDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean create(int accountIdAdmin, String groupName, String info) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_GROUP_BY_NAME)) {
            preparedStatement.setString(1, groupName);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (!resultSet.next()) {
                    insertRowGroup(accountIdAdmin, groupName, info);
                    return true;
                } else {
                    throw new DaoException("Group name \"" + groupName + "\" is already used.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public Group get(int id) throws DaoException {
        try (PreparedStatement preparedStatementGroup = this.connection.prepareStatement(SELECT_GROUP_BY_ID);
             PreparedStatement preparedStatementMembers = this.connection.prepareStatement(SELECT_ACCOUNT_ID_FROM_ACCOUNT_IN_GROUP)) {
            preparedStatementGroup.setInt(1, id);
            preparedStatementMembers.setInt(1, id);
            try (ResultSet resultSetGroup = preparedStatementGroup.executeQuery();
                 ResultSet resultSetMembers = preparedStatementMembers.executeQuery()) {
                if (resultSetGroup.next()) {
                    return createGroupFromResult(resultSetGroup, resultSetMembers);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Group> getAll() throws DaoException {
        try (ResultSet resultSetGroups = this.connection.createStatement().executeQuery(SELECT_ALL_SOC_GROUPS)) {
            List<Group> soc_groups = new ArrayList<>();
            while (resultSetGroups.next()) {
                try (PreparedStatement preparedStatement = this.connection.prepareStatement(SELECT_ACCOUNT_ID_FROM_ACCOUNT_IN_GROUP)) {
                    preparedStatement.setInt(1, resultSetGroups.getInt(COLUMN_GROUP_ID));
                    try (ResultSet resultSetMembers = preparedStatement.executeQuery()) {
                        soc_groups.add(createGroupFromResult(resultSetGroups, resultSetMembers));
                    }
                }
            }
            return soc_groups;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean update(Group group) throws DaoException {
        int id = group.getId();
        String name = group.getName();
        AccountDAO.executePrepStatementUpdate(id, name, this.connection, UPDATE_SOC_GROUPS_SET_NAME);
        String info = group.getInfo();
        AccountDAO.executePrepStatementUpdate(id, info, this.connection, UPDATE_SOC_GROUPS_SET_INFO);
        int accountIdAdmin = group.getAccountIdAdmin();
        if (accountIdAdmin != 0) {
            int currentIdAdmin = 0;
            try (PreparedStatement preparedStatementSelect = this.connection.prepareStatement(SELECT_ACCOUNT_ID_ADMIN_FROM_SOC_GROUPS)) {
                preparedStatementSelect.setInt(1, id);
                try (ResultSet resultSet = preparedStatementSelect.executeQuery()) {
                    if (resultSet.next()) {
                        currentIdAdmin = resultSet.getInt("account_id_admin");
                    }
                }
                try (PreparedStatement preparedStatementUpdate1 = this.connection.prepareStatement(UPDATE_SOC_GROUPS_SET_ACCOUNT_ID_ADMIN)) {
                    preparedStatementUpdate1.setInt(1, accountIdAdmin);
                    preparedStatementUpdate1.setInt(2, id);
                    preparedStatementUpdate1.executeUpdate();
                }
                try (PreparedStatement preparedStatementUpdate2 = this.connection.prepareStatement(UPDATE_ACCOUNT_IN_GROUP_SET_ACCOUNT_ID)) {
                    preparedStatementUpdate2.setInt(1, accountIdAdmin);
                    preparedStatementUpdate2.setInt(2, currentIdAdmin);
                    preparedStatementUpdate2.setInt(3, id);
                    preparedStatementUpdate2.executeUpdate();
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        try {
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean addMemberToGroup(int idGroup, int idNewMember) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_INTO_ACCOUNT_IN_GROUP)) {
            preparedStatement.setInt(1, idNewMember);
            preparedStatement.setInt(2, idGroup);
            preparedStatement.executeUpdate();
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean removeMemberFromGroup(int idGroup, int idMemberToDelete) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_FROM_ACCOUNT_IN_GROUP)) {
            preparedStatement.setInt(1, idMemberToDelete);
            preparedStatement.setInt(2, idGroup);
            preparedStatement.executeUpdate();
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean removeGroup(int idGroup) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE_FROM_SOC_GROUPS)) {
            preparedStatement.setInt(1, idGroup);
            preparedStatement.executeUpdate();
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public void closeConnection() {
        connectionPool.returnConnection(connection);
    }

    private boolean insertRowGroup(int accountIdAdmin, String groupName, String info) throws DaoException {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_INTO_SOC_GROUPS)) {
            preparedStatement.setString(1, groupName);
            preparedStatement.setString(2, info);
            preparedStatement.setInt(3, accountIdAdmin);
            preparedStatement.executeUpdate();
            int groupIdToInsert = -1;
            try (PreparedStatement preparedStatementGroupID = this.connection.prepareStatement(SELECT_GROUP_ID_FROM_SOC_GROUPS)) {
                preparedStatementGroupID.setString(1, groupName);
                try (ResultSet resultSet = preparedStatementGroupID.executeQuery()) {
                    if (resultSet.next()) {
                        groupIdToInsert = resultSet.getInt(COLUMN_GROUP_ID);
                    }
                }
            }
            try (PreparedStatement preparedStatementInsertAccountsInGroupTable = this.connection
                    .prepareStatement(INSERT_INTO_ACCOUNT_IN_GROUP)) {
                preparedStatementInsertAccountsInGroupTable.setInt(1, accountIdAdmin);
                preparedStatementInsertAccountsInGroupTable.setInt(2, groupIdToInsert);
                preparedStatementInsertAccountsInGroupTable.executeUpdate();
            }
            this.connection.commit();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Group createGroupFromResult(ResultSet resultSetGroup, ResultSet resultSetMembers) throws SQLException {
        Group group = new Group();
        group.setId(resultSetGroup.getInt(COLUMN_GROUP_ID));
        group.setName(resultSetGroup.getString("name"));
        group.setInfo(resultSetGroup.getString("info"));
        group.setAccountIdAdmin(resultSetGroup.getInt("account_id_admin"));
        List<Integer> members = new ArrayList<>();
        while (resultSetMembers.next()) {
            members.add(resultSetMembers.getInt("account_id"));
        }
        members.sort(null);
        group.setMembersId(members);
        return group;
    }
}