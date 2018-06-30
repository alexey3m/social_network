package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoException;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GroupDAOTest {
    private ConnectionPool connectionPool;

    @Before
    public void initDB() throws IOException, SQLException {
        connectionPool = new ConnectionPool();
        ScriptRunnerUtil runner = new ScriptRunnerUtil(connectionPool.getConnection(), true, true);
        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/" +
                "dao/src/test/resources/create-data-model.sql")));
        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/" +
                "dao/src/test/resources/fillDB.sql")));
    }

    @After
    public void terminateTables() {
        try (Statement statement = connectionPool.getConnection().createStatement()) {
            statement.execute("DROP TABLE message, account_in_group, soc_group, relationship, phone, account");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTest() throws DaoException, DaoNameException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        boolean result = groupDAO.create("Group 3", null, null, "2018-06-13",
                "Info 3", 2);
        List<Integer> acceptedMembersId = new ArrayList<>();
        acceptedMembersId.add(2);
        List<Integer> pendingMembersId = new ArrayList<>();
        List<Integer> adminsId = new ArrayList<>();
        adminsId.add(2);
        Group expected = new Group(3, "Group 3", null, null, "2018-06-13",
                "Info 3", 2, acceptedMembersId, pendingMembersId, adminsId);
        assertTrue(result);
        assertEquals(expected, groupDAO.get(3));
    }

    @Test(expected = DaoNameException.class)
    public void createExceptionTest() throws DaoException, DaoNameException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.create("Group 3", null, null, "2018-06-13", "Info 3", 2);
        groupDAO.create("Group 3", null, null, "2018-06-13", "Info 3", 2);
    }

    @Test
    public void getTest() throws DaoException {
        List<Integer> acceptedMembersId = new ArrayList<>();
        acceptedMembersId.add(1);
        acceptedMembersId.add(3);
        List<Integer> pendingMembersId = new ArrayList<>();
        List<Integer> adminsId = new ArrayList<>();
        adminsId.add(1);
        Group expected = new Group(1, "Group 1", null, null, "2018-06-07",
                "Info 1", 1, acceptedMembersId, pendingMembersId, adminsId);
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void getAllTest() throws DaoException {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group1 = new Group(1, "Group 1", null, null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        List<Integer> acceptedMembersId2 = new ArrayList<>();
        acceptedMembersId2.add(2);
        List<Integer> pendingMembersId2 = new ArrayList<>();
        List<Integer> adminsId2 = new ArrayList<>();
        adminsId2.add(2);
        Group group2 = new Group(2, "Group 2", null, null, "2018-06-09",
                "Info 2", 2, acceptedMembersId2, pendingMembersId2, adminsId2);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        expected.add(group2);
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(expected, groupDAO.getAll());
    }

    @Test
    public void getAllByIdTest() throws DaoException {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group1 = new Group(1, "Group 1", null, null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(expected, groupDAO.getAllById(1));
    }

    @Test
    public void searchByStringTest() throws DaoException {
        List<Integer> acceptedMembersId1 = new ArrayList<>();
        acceptedMembersId1.add(1);
        acceptedMembersId1.add(3);
        List<Integer> pendingMembersId1 = new ArrayList<>();
        List<Integer> adminsId1 = new ArrayList<>();
        adminsId1.add(1);
        Group group1 = new Group(1, "Group 1", null, null, "2018-06-07",
                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
        List<Integer> acceptedMembersId2 = new ArrayList<>();
        acceptedMembersId2.add(2);
        List<Integer> pendingMembersId2 = new ArrayList<>();
        List<Integer> adminsId2 = new ArrayList<>();
        adminsId2.add(2);
        Group group2 = new Group(2, "Group 2", null, null, "2018-06-09",
                "Info 2", 2, acceptedMembersId2, pendingMembersId2, adminsId2);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        expected.add(group2);
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(expected, groupDAO.searchByString("Gr"));
    }

    @Test
    public void getIdTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(2, groupDAO.getId("Group 2"));
    }

    @Test
    public void getRoleMemberInGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(GroupRole.ADMIN, groupDAO.getRoleMemberInGroup(1, 1));
        assertEquals(GroupRole.USER, groupDAO.getRoleMemberInGroup(1, 3));
        assertEquals(GroupRole.UNKNOWN, groupDAO.getRoleMemberInGroup(1, 2));
    }

    @Test
    public void getStatusMemberInGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        assertEquals(GroupStatus.ACCEPTED, groupDAO.getStatusMemberInGroup(1, 1));
    }

    @Test
    public void updateGroupNameTest() throws DaoException {
        Group groupToUpdate = new Group();
        groupToUpdate.setId(1);
        groupToUpdate.setName("NEW name group 1");
        List<Integer> acceptedMembersId = new ArrayList<>();
        acceptedMembersId.add(1);
        acceptedMembersId.add(3);
        List<Integer> pendingMembersId = new ArrayList<>();
        List<Integer> adminsId = new ArrayList<>();
        adminsId.add(1);
        Group expected = new Group(1, "NEW name group 1", null, null, "2018-06-07",
                "Info 1", 1, acceptedMembersId, pendingMembersId, adminsId);
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.update(groupToUpdate);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void updateGroupInfoTest() throws DaoException {
        Group groupToUpdate = new Group();
        groupToUpdate.setId(1);
        groupToUpdate.setInfo("NEW info group 1");

        List<Integer> acceptedMembersId = new ArrayList<>();
        acceptedMembersId.add(1);
        acceptedMembersId.add(3);
        List<Integer> pendingMembersId = new ArrayList<>();
        List<Integer> adminsId = new ArrayList<>();
        adminsId.add(1);
        Group expected = new Group(1, "Group 1", null, null, "2018-06-07",
                "NEW info group 1", 1, acceptedMembersId, pendingMembersId, adminsId);

        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.update(groupToUpdate);
        assertEquals(expected, groupDAO.get(1));
    }


    @Test
    public void addPendingMemberToGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        assertTrue(groupDAO.addPendingMemberToGroup(1, 2));
        assertEquals(expected, groupDAO.get(1).getPendingMembersId());
    }

    @Test
    public void setStatusMemberInGroupAcceptTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.addPendingMemberToGroup(1, 2);
        assertTrue(groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED));
        List<Integer> expectedPending = new ArrayList<>();
        List<Integer> expectedAccepted = new ArrayList<>();
        expectedAccepted.add(1);
        expectedAccepted.add(3);
        expectedAccepted.add(2);
        assertEquals(expectedPending, groupDAO.get(1).getPendingMembersId());
        assertEquals(expectedAccepted, groupDAO.get(1).getAcceptedMembersId());
    }

    @Test
    public void setRoleMemberInGroupAcceptTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.addPendingMemberToGroup(1, 2);
        groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED);
        assertTrue(groupDAO.setRoleMemberInGroup(1, 2, GroupRole.ADMIN));
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(2);
        assertEquals(expected, groupDAO.get(1).getAdminsId());
    }

    @Test
    public void removeMemberFromGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.addPendingMemberToGroup(1, 2);
        groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED);
        assertTrue(groupDAO.removeMemberFromGroup(1, 2));
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(3);
        assertEquals(expected, groupDAO.get(1).getAcceptedMembersId());
    }

    @Test
    public void removeGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connectionPool);
        groupDAO.remove(1);
        assertNull(groupDAO.get(1));
    }
}