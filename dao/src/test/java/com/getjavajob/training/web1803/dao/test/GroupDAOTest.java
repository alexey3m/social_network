package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.AccountInGroup;
import com.getjavajob.training.web1803.common.Group;
import com.getjavajob.training.web1803.common.enums.GroupRole;
import com.getjavajob.training.web1803.common.enums.GroupStatus;
import com.getjavajob.training.web1803.dao.GroupDAO;
import com.getjavajob.training.web1803.dao.exceptions.DaoNameException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
@Transactional
public class GroupDAOTest {

    @Autowired
    private GroupDAO groupDAO;

    @Test
    public void createTest() throws DaoNameException {
        List<AccountInGroup> accounts = new ArrayList<>();
        accounts.add(new AccountInGroup(0, 2, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        Group group = new Group(0, "Group 3", null, "2018-06-13",
                "Info 3", 2, accounts);
        boolean result = groupDAO.create(group);
        Group expected = new Group(3, "Group 3", null, "2018-06-13",
                "Info 3", 2, accounts);
        assertTrue(result);
        assertEquals(expected, groupDAO.get(3));
    }

    @Test(expected = DaoNameException.class)
    public void createExceptionTest() throws DaoNameException {
        List<AccountInGroup> accounts = new ArrayList<>();
        accounts.add(new AccountInGroup(0, 2, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        Group group = new Group(0, "Group 3", null, "2018-06-13",
                "Info 3", 2, accounts);
        groupDAO.create(group);
        groupDAO.create(group);
    }

    @Test
    public void getTest() {
        List<AccountInGroup> accounts = new ArrayList<>();
        accounts.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        accounts.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        Group expected = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, accounts);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void getAllTest() {
        List<AccountInGroup> accounts1 = new ArrayList<>();
        accounts1.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        accounts1.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, accounts1);
        List<AccountInGroup> accounts2 = new ArrayList<>();
        accounts2.add(new AccountInGroup(3, 2, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        Group group2 = new Group(2, "Group 2", null, "2018-06-09",
                "Info 2", 2, accounts2);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        expected.add(group2);
        assertEquals(expected, groupDAO.getAll());
    }

    @Test
    public void getAllByUserIdTest() {
        List<AccountInGroup> accounts1 = new ArrayList<>();
        accounts1.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        accounts1.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, accounts1);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        assertEquals(expected, groupDAO.getAllByUserId(1));
    }

    @Test
    public void searchByStringTest() {
        List<AccountInGroup> accounts1 = new ArrayList<>();
        accounts1.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        accounts1.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
                "Info 1", 1, accounts1);
        List<Group> expected = new ArrayList<>();
        expected.add(group1);
        assertEquals(expected, groupDAO.searchByString("1"));
    }

    @Test
    public void getIdTest() {
        assertEquals(2, groupDAO.getId("Group 2"));
    }

    @Test
    public void getRoleMemberInGroupTest() {
        assertEquals(GroupRole.ADMIN, groupDAO.getRoleMemberInGroup(1, 1));
        assertEquals(GroupRole.USER, groupDAO.getRoleMemberInGroup(1, 3));
        assertEquals(GroupRole.UNKNOWN, groupDAO.getRoleMemberInGroup(1, 2));
    }

    @Test
    public void getStatusMemberInGroupTest() {
        assertEquals(GroupStatus.ACCEPTED, groupDAO.getStatusMemberInGroup(1, 1));
    }

    @Test
    public void updateGroupInfoTest() {
        Group groupToUpdate = groupDAO.get(1);
        groupToUpdate.setId(1);
        groupToUpdate.setName("Group 1");
        groupToUpdate.setInfo("NEW info group 1");
        List<AccountInGroup> accounts = new ArrayList<>();
        accounts.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        accounts.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        Group expected = new Group(1, "Group 1", null, "2018-06-07",
                "NEW info group 1", 1, accounts);
        groupDAO.update(groupToUpdate);
        assertEquals(expected, groupDAO.get(1));
    }

    @Test
    public void addPendingMemberToGroupTest() {
        List<AccountInGroup> expected = new ArrayList<>();
        expected.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(4, 2, GroupRole.USER, GroupStatus.PENDING));
        assertTrue(groupDAO.addPendingMemberToGroup(1, 2));
        assertEquals(expected, groupDAO.get(1).getAccounts());
    }

    @Test
    public void setStatusMemberInGroupAcceptTest() {
        groupDAO.addPendingMemberToGroup(1, 2);
        assertTrue(groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED));
        List<AccountInGroup> expected = new ArrayList<>();
        expected.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(4, 2, GroupRole.USER, GroupStatus.ACCEPTED));
        assertEquals(expected, groupDAO.get(1).getAccounts());
    }

    @Test
    public void setRoleMemberInGroupAcceptTest() {
        groupDAO.addPendingMemberToGroup(1, 2);
        groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED);
        assertTrue(groupDAO.setRoleMemberInGroup(1, 2, GroupRole.ADMIN));
        List<AccountInGroup> expected = new ArrayList<>();
        expected.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(4, 2, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        assertEquals(expected, groupDAO.get(1).getAccounts());
    }

    @Test
    public void removeMemberFromGroupTest() {
        groupDAO.addPendingMemberToGroup(1, 2);
        groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED);
        assertTrue(groupDAO.removeMemberFromGroup(1, 2));
        List<AccountInGroup> expected = new ArrayList<>();
        expected.add(new AccountInGroup(1, 1, GroupRole.ADMIN, GroupStatus.ACCEPTED));
        expected.add(new AccountInGroup(2, 3, GroupRole.USER, GroupStatus.ACCEPTED));
        assertEquals(expected, groupDAO.get(1).getAccounts());
    }

    @Test
    public void removeGroupTest() {
        assertTrue(groupDAO.remove(1));
        assertNull(groupDAO.get(1));
    }

    @Configuration
    @PropertySource("classpath:H2Connect.properties")
    static class Config {

        @Autowired
        private Environment env;

        @Bean
        public DataSource dataSource() {
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setUrl(env.getProperty("database.url"));
            dataSource.setUsername(env.getProperty("database.user"));
            dataSource.setPassword(env.getProperty("database.password"));
            return dataSource;
        }

        @Bean
        public JdbcTemplate jdbcTemplate() {
            return new JdbcTemplate(dataSource());
        }


    }
}