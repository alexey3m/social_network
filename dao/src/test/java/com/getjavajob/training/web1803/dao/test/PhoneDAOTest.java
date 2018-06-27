package com.getjavajob.training.web1803.dao.test;

public class PhoneDAOTest {
    private ConnectionPool connectionPool;

//    @Before
//    public void initDB() throws IOException, SQLException {
//        connectionPool = new ConnectionPool();
//        ScriptRunnerUtil runner = new ScriptRunnerUtil(connectionPool.getConnection(), true, true);
//        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/create-data-model.sql")));
//        runner.runScript(new BufferedReader(new FileReader("e:/test/dev/projects/getjavajob/social-network-app/dao/src/test/resources/fillDB.sql")));
//
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
//    public void createTest() throws DaoException {
//        Map<String, PhoneType> expected = new HashMap<>();
//        expected.put("800", PhoneType.HOME);
//        PhoneDAO phoneDAO = new PhoneDAO(connectionPool);
//        boolean result = phoneDAO.create(2, "800", PhoneType.HOME);
//        assertTrue(result);
//        assertEquals(expected, phoneDAO.getAll(2));
//    }
//
//    @Test
//    public void getAllTest() throws DaoException {
//        Map<String, PhoneType> expected = new HashMap<>();
//        expected.put("900", PhoneType.HOME);
//        expected.put("901", PhoneType.WORK);
//        PhoneDAO phoneDAO = new PhoneDAO(connectionPool);
//        assertEquals(expected, phoneDAO.getAll(1));
//    }
//
//    @Test
//    public void updateTest() throws DaoException {
//        Map<String, PhoneType> newPhones = new HashMap<>();
//        newPhones.put("800", PhoneType.WORK);
//        newPhones.put("801", PhoneType.ADDITIONAL);
//        PhoneDAO phoneDAO = new PhoneDAO(connectionPool);
//        boolean result = phoneDAO.update(1, newPhones);
//        Map<String, PhoneType> expected = new HashMap<>();
//        expected.put("800", PhoneType.WORK);
//        expected.put("801", PhoneType.ADDITIONAL);
//        assertTrue(result);
//        assertEquals(expected, phoneDAO.getAll(1));
//    }
//
//    @Test
//    public void removeTest() throws DaoException {
//        Map<String, PhoneType> expected = new HashMap<>();
//        PhoneDAO phoneDAO = new PhoneDAO(connectionPool);
//        phoneDAO.remove(1);
//        assertEquals(expected, phoneDAO.getAll(1));
//    }
}