package com.getjavajob.training.web1803.dao.test;

import com.getjavajob.training.web1803.common.Relationship;
import com.getjavajob.training.web1803.common.enums.Status;
import com.getjavajob.training.web1803.dao.RelationshipDAO;
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
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:socnet-context-test.xml")
@SqlGroup({
        @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = {"classpath:create-data-model.sql", "classpath:fillDB.sql"}),
        @Sql(executionPhase = AFTER_TEST_METHOD, scripts = "classpath:remove.sql")
})
@Transactional
public class RelationshipDAOTest {

    @Autowired
    private RelationshipDAO relationshipDAO;

    @Test
    public void getFriendsIdListTest() {
        List<Integer> expectedId1 = new ArrayList<>();
        List<Integer> expectedId2 = new ArrayList<>();
        expectedId2.add(3);
        assertEquals(expectedId1, relationshipDAO.getFriendsIdList(1));
        assertEquals(expectedId2, relationshipDAO.getFriendsIdList(2));
    }

    @Test
    public void getPendingRequestToIdTest() {
        List<Integer> expectedId1 = new ArrayList<>();
        expectedId1.add(1);
        List<Integer> expectedId2 = new ArrayList<>();
        assertEquals(expectedId1, relationshipDAO.getPendingRequestToId(2));
        assertEquals(expectedId2, relationshipDAO.getPendingRequestToId(1));
    }

    @Test
    public void getFriendRequestsFromIdTest() {
        List<Integer> expectedId1 = new ArrayList<>();
        expectedId1.add(2);
        expectedId1.add(3);
        List<Integer> expectedId2 = new ArrayList<>();
        assertEquals(expectedId1, relationshipDAO.getFriendRequestsFromId(1));
        assertEquals(expectedId2, relationshipDAO.getFriendRequestsFromId(3));
    }

    @Test
    public void getStatusTest() {
        assertEquals(Status.PENDING, relationshipDAO.getStatus(new Relationship(1, 2, null, 0)));
        assertEquals(Status.ACCEPTED, relationshipDAO.getStatus(new Relationship(2, 3, null, 0)));
    }

    @Test
    public void getPendingRequestToMeTest() {
        assertEquals(Status.PENDING, relationshipDAO.getPendingRequestToMe(new Relationship(1, 3, null, 1)));
    }

    @Test
    public void updateQueryFriendDeclineTest() {
        assertTrue(relationshipDAO.updateQueryFriend(new Relationship(1, 2, Status.DECLINE, 2)));
        assertEquals(Status.DECLINE, relationshipDAO.getStatus(new Relationship(1, 2, null, 0)));
    }

    @Test
    public void updateQueryFriendAcceptTest() {
        List<Integer> expectedId = new ArrayList<>();
        expectedId.add(1);
        expectedId.add(3);
        assertTrue(relationshipDAO.updateQueryFriend(new Relationship(1, 2, Status.ACCEPTED, 2)));
        assertEquals(Status.ACCEPTED, relationshipDAO.getStatus(new Relationship(1, 2, null, 0)));
        assertEquals(expectedId, relationshipDAO.getFriendsIdList(2));
    }

    @Test
    public void removeFriendTest() {
        assertTrue(relationshipDAO.removeFriend(new Relationship(2, 3, null, 0)));
        assertEquals(Status.UNKNOWN, relationshipDAO.getStatus(new Relationship(2, 3, null, 0)));
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