import exceptions.DaoException;

public class Run {
    public static void main(String[] args) throws DaoException {
        AccountDAO accountDAO = new AccountDAO();
        System.out.println(accountDAO.get(1));
    }
}
