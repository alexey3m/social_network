package com.getjavajob.training.web1803.dao.test;

import org.apache.commons.dbcp2.BasicDataSource;
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

import javax.sql.DataSource;

import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
public class GroupDAOTest {
//
//    @Autowired
//    private GroupDAO groupDAO;
//
//    @Test
//    public void createTest() throws DaoNameException {
//        Group group = new Group(0, "Group 3", null, "2018-06-13",
//                "Info 3", 2, null, null, null);
//        boolean result = groupDAO.create(group);
//        List<Integer> acceptedMembersId = new ArrayList<>();
//        acceptedMembersId.add(2);
//        List<Integer> pendingMembersId = new ArrayList<>();
//        List<Integer> adminsId = new ArrayList<>();
//        adminsId.add(2);
//        Group expected = new Group(3, "Group 3", null, "2018-06-13",
//                "Info 3", 2, acceptedMembersId, pendingMembersId, adminsId);
//        assertTrue(result);
//        assertEquals(expected, groupDAO.get(3));
//    }
//
//    @Test(expected = DaoNameException.class)
//    public void createExceptionTest() throws DaoNameException {
//        Group group = new Group(0, "Group 3", null, "2018-06-13",
//                "Info 3", 2, null, null, null);
//        groupDAO.create(group);
//        groupDAO.create(group);
//    }
//
//    @Test
//    public void getTest() {
//        List<Integer> acceptedMembersId = new ArrayList<>();
//        acceptedMembersId.add(1);
//        acceptedMembersId.add(3);
//        List<Integer> pendingMembersId = new ArrayList<>();
//        List<Integer> adminsId = new ArrayList<>();
//        adminsId.add(1);
//        Group expected = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId, pendingMembersId, adminsId);
//        assertEquals(expected, groupDAO.get(1));
//    }
//
//    @Test
//    public void getAllTest() {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        List<Integer> acceptedMembersId2 = new ArrayList<>();
//        acceptedMembersId2.add(2);
//        List<Integer> pendingMembersId2 = new ArrayList<>();
//        List<Integer> adminsId2 = new ArrayList<>();
//        adminsId2.add(2);
//        Group group2 = new Group(2, "Group 2", null, "2018-06-09",
//                "Info 2", 2, acceptedMembersId2, pendingMembersId2, adminsId2);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        expected.add(group2);
//        assertEquals(expected, groupDAO.getAll());
//    }
//
//    @Test
//    public void getAllByIdTest() {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        assertEquals(expected, groupDAO.getAllByUserId(1));
//    }
//
//    @Test
//    public void searchByStringTest() {
//        List<Integer> acceptedMembersId1 = new ArrayList<>();
//        acceptedMembersId1.add(1);
//        acceptedMembersId1.add(3);
//        List<Integer> pendingMembersId1 = new ArrayList<>();
//        List<Integer> adminsId1 = new ArrayList<>();
//        adminsId1.add(1);
//        Group group1 = new Group(1, "Group 1", null, "2018-06-07",
//                "Info 1", 1, acceptedMembersId1, pendingMembersId1, adminsId1);
//        List<Integer> acceptedMembersId2 = new ArrayList<>();
//        acceptedMembersId2.add(2);
//        List<Integer> pendingMembersId2 = new ArrayList<>();
//        List<Integer> adminsId2 = new ArrayList<>();
//        adminsId2.add(2);
//        Group group2 = new Group(2, "Group 2", null, "2018-06-09",
//                "Info 2", 2, acceptedMembersId2, pendingMembersId2, adminsId2);
//        List<Group> expected = new ArrayList<>();
//        expected.add(group1);
//        expected.add(group2);
//        assertEquals(expected, groupDAO.searchByString("Gr"));
//    }
//
//    @Test
//    public void getIdTest() {
//        assertEquals(2, groupDAO.getId("Group 2"));
//    }
//
//    @Test
//    public void getRoleMemberInGroupTest() {
//        assertEquals(GroupRole.ADMIN, groupDAO.getRoleMemberInGroup(1, 1));
//        assertEquals(GroupRole.USER, groupDAO.getRoleMemberInGroup(1, 3));
//        assertEquals(GroupRole.UNKNOWN, groupDAO.getRoleMemberInGroup(1, 2));
//    }
//
//    @Test
//    public void getStatusMemberInGroupTest() {
//        assertEquals(GroupStatus.ACCEPTED, groupDAO.getStatusMemberInGroup(1, 1));
//    }
//
//    @Test
//    public void updateGroupInfoTest() {
//        Group groupToUpdate = new Group();
//        groupToUpdate.setId(1);
//        groupToUpdate.setName("Group 1");
//        groupToUpdate.setInfo("NEW info group 1");
//        List<Integer> acceptedMembersId = new ArrayList<>();
//        acceptedMembersId.add(1);
//        acceptedMembersId.add(3);
//        List<Integer> pendingMembersId = new ArrayList<>();
//        List<Integer> adminsId = new ArrayList<>();
//        adminsId.add(1);
//        Group expected = new Group(1, "Group 1", null, "2018-06-07",
//                "NEW info group 1", 1, acceptedMembersId, pendingMembersId, adminsId);
//        groupDAO.update(groupToUpdate);
//        assertEquals(expected, groupDAO.get(1));
//    }
//
//    @Test
//    public void addPendingMemberToGroupTest() {
//        List<Integer> expected = new ArrayList<>();
//        expected.add(2);
//        assertTrue(groupDAO.addPendingMemberToGroup(1, 2));
//        assertEquals(expected, groupDAO.get(1).getPendingMembersId());
//    }
//
//    @Test
//    public void setStatusMemberInGroupAcceptTest() {
//        groupDAO.addPendingMemberToGroup(1, 2);
//        assertTrue(groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED));
//        List<Integer> expectedPending = new ArrayList<>();
//        List<Integer> expectedAccepted = new ArrayList<>();
//        expectedAccepted.add(1);
//        expectedAccepted.add(3);
//        expectedAccepted.add(2);
//        assertEquals(expectedPending, groupDAO.get(1).getPendingMembersId());
//        assertEquals(expectedAccepted, groupDAO.get(1).getAcceptedMembersId());
//    }
//
//    @Test
//    public void setRoleMemberInGroupAcceptTest() {
//        groupDAO.addPendingMemberToGroup(1, 2);
//        groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED);
//        assertTrue(groupDAO.setRoleMemberInGroup(1, 2, GroupRole.ADMIN));
//        List<Integer> expected = new ArrayList<>();
//        expected.add(1);
//        expected.add(2);
//        assertEquals(expected, groupDAO.get(1).getAdminsId());
//    }
//
//    @Test
//    public void removeMemberFromGroupTest() {
//        groupDAO.addPendingMemberToGroup(1, 2);
//        groupDAO.setStatusMemberInGroup(1, 2, GroupStatus.ACCEPTED);
//        assertTrue(groupDAO.removeMemberFromGroup(1, 2));
//        List<Integer> expected = new ArrayList<>();
//        expected.add(1);
//        expected.add(3);
//        assertEquals(expected, groupDAO.get(1).getAcceptedMembersId());
//    }
//
//    @Test
//    public void removeGroupTest() {
//        assertTrue(groupDAO.remove(1));
//        assertNull(groupDAO.get(1));
//    }

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