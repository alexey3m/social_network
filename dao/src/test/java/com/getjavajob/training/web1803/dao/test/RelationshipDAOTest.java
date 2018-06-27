package com.getjavajob.training.web1803.dao.test;

public class RelationshipDAOTest {
    private ConnectionPool connectionPool;

//    @Before
//    public void initDB() throws IOException, SQLException {
//        connectionPool = new ConnectionPool();
//        ScriptRunnerUtil runner = new ScriptRunnerUtil(connectionPool.getConnection(), true, true);
//        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/" +
//                "dao/src/test/resources/create-data-model.sql")));
//        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/" +
//                "dao/src/test/resources/fillDB.sql")));
//    }
//
//    @After
//    public void terminateTables() {
//        try (Statement statement = connectionPool.getConnection().createStatement()) {
//            statement.execute("DROP TABLE message, account_in_group, soc_group, relationship, phone, account");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void getFriendsIdListTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        List<Integer> expectedId1 = new ArrayList<>();
//        List<Integer> expectedId2 = new ArrayList<>();
//        expectedId2.add(3);
//        assertEquals(expectedId1, relationshipDAO.getFriendsIdList(1));
//        assertEquals(expectedId2, relationshipDAO.getFriendsIdList(2));
//    }
//
//    @Test
//    public void getPendingRequestToIdTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        List<Integer> expectedId1 = new ArrayList<>();
//        expectedId1.add(1);
//        List<Integer> expectedId2 = new ArrayList<>();
//        assertEquals(expectedId1, relationshipDAO.getPendingRequestToId(2));
//        assertEquals(expectedId2, relationshipDAO.getPendingRequestToId(1));
//    }
//
//    @Test
//    public void getFriendRequestsFromIdTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        List<Integer> expectedId1 = new ArrayList<>();
//        expectedId1.add(2);
//        expectedId1.add(3);
//        List<Integer> expectedId2 = new ArrayList<>();
//        assertEquals(expectedId1, relationshipDAO.getFriendRequestsFromId(1));
//        assertEquals(expectedId2, relationshipDAO.getFriendRequestsFromId(3));
//    }
//
//    @Test
//    public void getStatusTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        assertEquals(Status.PENDING, relationshipDAO.getStatus(1, 2));
//        assertEquals(Status.ACCEPTED, relationshipDAO.getStatus(2, 3));
//    }
//
//    @Test
//    public void getPendingRequestToMeTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        assertEquals(Status.PENDING, relationshipDAO.getPendingRequestToMe(1, 3, 1));
//    }
//
//    @Test
//    public void updateQueryFriendDeclineTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        assertTrue(relationshipDAO.updateQueryFriend(1, 2, Status.DECLINE.getStatus(), 2));
//        assertEquals(Status.DECLINE, relationshipDAO.getStatus(1, 2));
//    }
//
//    @Test
//    public void updateQueryFriendAcceptTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        List<Integer> expectedId = new ArrayList<>();
//        expectedId.add(1);
//        expectedId.add(3);
//        assertTrue(relationshipDAO.updateQueryFriend(1, 2, Status.ACCEPTED.getStatus(), 2));
//        assertEquals(Status.ACCEPTED, relationshipDAO.getStatus(1, 2));
//        assertEquals(expectedId, relationshipDAO.getFriendsIdList(2));
//    }
//
//    @Test
//    public void removeFriendTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        assertTrue(relationshipDAO.removeFriend(2, 3));
//        assertEquals(Status.UNKNOWN, relationshipDAO.getStatus(2, 3));
//    }
//
//    @Test
//    public void createQueryFriendTest() throws DaoException {
//        RelationshipDAO relationshipDAO = new RelationshipDAO(connectionPool);
//        relationshipDAO.removeFriend(2, 3);
//        assertTrue(relationshipDAO.createQueryFriend(2, 3, 2));
//        List<Integer> expectedId = new ArrayList<>();
//        expectedId.add(1);
//        expectedId.add(2);
//        assertEquals(expectedId, relationshipDAO.getPendingRequestToId(3));
//    }
}