import exceptions.DaoException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GroupDAOTest {
    private Connection connection;

    @Before
    public void initDB() {
        try {
            Properties properties = new Properties();
            properties.load(this.getClass().getClassLoader().getResourceAsStream("H2connect.properties"));
            String url = properties.getProperty("database.url");
            String user = properties.getProperty("database.user");
            String password = properties.getProperty("database.password");
            this.connection = DriverManager.getConnection(url, user, password);
            this.connection.setAutoCommit(false);
            ScriptRunnerUtil runner = new ScriptRunnerUtil(connection, true, true);
            runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/create-data-model.sql")));
            runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/fillDB.sql")));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void terminateTables() {
        try (Statement statement = this.connection.createStatement()) {
            statement.execute("DROP TABLE accounts, account_info, groups, account_in_group, relationship");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connection);
        boolean result = groupDAO.create(3, "Group3", "Info3");
        List<Integer> members = new ArrayList<>();
        members.add(3);
        Group expected = new Group(3, "Group3", "Info3", 3, members);
        assertTrue(result);
        assertEquals(expected, groupDAO.get(3));
    }

    @Test(expected = DaoException.class)
    public void createExceptionTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.create(3, "Group3", "Info3");
        groupDAO.create(3, "Group3", "Info3");
    }

    @Test
    public void getTest() throws DaoException {
        List<Integer> members = new ArrayList<>();
        members.add(1);
        members.add(3);
        Group expected = new Group(1, "Group 1", "Info 1", 1, members);
        GroupDAO groupDAO = new GroupDAO(connection);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void getAllTest() throws DaoException {
        List<Integer> members1 = new ArrayList<>();
        members1.add(1);
        members1.add(3);
        Group group1 = new Group(1, "Group 1", "Info 1", 1, members1);
        List<Integer> members2 = new ArrayList<>();
        members2.add(2);
        Group group2 = new Group(2, "Group 2", "Info 2", 2, members2);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        expected.add(group2);
        GroupDAO groupDAO = new GroupDAO(connection);
        assertEquals(expected, groupDAO.getAll());
    }

    @Test
    public void updateGroupNameTest() throws DaoException {
        Group groupToUpdate = new Group();
        groupToUpdate.setId(1);
        groupToUpdate.setName("NEW name group 1");

        List<Integer> members1 = new ArrayList<>();
        members1.add(1);
        members1.add(3);
        Group expected = new Group(1, "NEW name group 1", "Info 1", 1, members1);

        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.update(groupToUpdate);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void updateGroupInfoTest() throws DaoException {
        Group groupToUpdate = new Group();
        groupToUpdate.setId(1);
        groupToUpdate.setInfo("NEW info group 1");

        List<Integer> members1 = new ArrayList<>();
        members1.add(1);
        members1.add(3);
        Group expected = new Group(1, "Group 1", "NEW info group 1", 1, members1);

        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.update(groupToUpdate);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void updateGroupAccountIdAdminTest() throws DaoException {
        Group groupToUpdate = new Group();
        groupToUpdate.setId(1);
        groupToUpdate.setAccountIdAdmin(2);

        List<Integer> members1 = new ArrayList<>();
        members1.add(2);
        members1.add(3);
        Group expected = new Group(1, "Group 1", "Info 1", 2, members1);

        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.update(groupToUpdate);
        assertEquals(expected, groupDAO.get(1));
    }


    @Test
    public void addMemberToGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.addMemberToGroup(1, 2);
        assertEquals(3, groupDAO.get(1).getMembersId().size());
    }

    @Test
    public void deleteMemberFromGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.removeMemberFromGroup(1, 3);

        List<Integer> members1 = new ArrayList<>();
        members1.add(1);
        Group expected = new Group(1, "Group 1", "Info 1", 1, members1);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void deleteGroupTest() throws DaoException {
        GroupDAO groupDAO = new GroupDAO(connection);
        groupDAO.removeGroup(1);
        assertEquals(1, groupDAO.getAll().size());
    }
}