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

public class RelationshipDAOTest {

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
    public void getFriendsIdListTest() throws DaoException {
        RelationshipDAO relationshipDAO = new RelationshipDAO(connection);
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        assertEquals(expected, relationshipDAO.getFriendsIdList(1));
    }

    @Test
    public void addFriendTest() throws DaoException {
        RelationshipDAO relationshipDAO = new RelationshipDAO(connection);
        List<Integer> expected = new ArrayList<>();
        expected.add(2);
        expected.add(3);
        expected.add(4);
        boolean result = relationshipDAO.addFriend(1, 4);
        assertTrue(result);
        assertEquals(expected, relationshipDAO.getFriendsIdList(1));
    }

    @Test
    public void removeFriendTest() throws DaoException {
        RelationshipDAO relationshipDAO = new RelationshipDAO(connection);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(4);
        boolean result = relationshipDAO.removeFriend(2, 3);
        assertTrue(result);
        assertEquals(expected, relationshipDAO.getFriendsIdList(3));
    }
}